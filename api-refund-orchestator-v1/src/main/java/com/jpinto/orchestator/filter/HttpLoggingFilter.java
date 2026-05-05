package com.jpinto.orchestator.filter;

import com.jpinto.orchestator.config.logging.LoggingProperties;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.util.*;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
@Slf4j
public class HttpLoggingFilter extends OncePerRequestFilter {


    private static final Set<String> LOGGABLE_CONTENT_TYPES = Set.of(
            "application/json",
            "application/xml",
            "application/x-www-form-urlencoded"
    );

    private static final String MDC_TRACE_ID       = "traceId";
    private static final String MDC_CORRELATION_ID = "correlationId";

    private final LoggingProperties props;

    public HttpLoggingFilter(LoggingProperties props) {
        this.props = props;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        return props.getExcludedPaths().stream().anyMatch(path::startsWith);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        var wrappedRequest  = new ContentCachingRequestWrapper(request, props.getMaxBodySize());
        var wrappedResponse = new ContentCachingResponseWrapper(response);

        // Extract or generate distributed trace IDs
        String traceId = Optional.ofNullable(request.getHeader("X-Trace-Id"))
                .filter(s -> !s.isBlank())
                .orElseGet(() -> UUID.randomUUID().toString());

        String correlationId = Optional.ofNullable(request.getHeader("X-Correlation-Id"))
                .filter(s -> !s.isBlank())
                .orElseGet(() -> UUID.randomUUID().toString());

        // Store in MDC — every log line emitted during this request
        // (by any class, not just this filter) will carry these IDs
        MDC.put(MDC_TRACE_ID,       traceId);
        MDC.put(MDC_CORRELATION_ID, correlationId);

        // Propagate IDs back to the caller via response headers
        wrappedResponse.addHeader("X-Trace-Id",       traceId);
        wrappedResponse.addHeader("X-Correlation-Id", correlationId);

        long startTime = System.currentTimeMillis();
        try {
            filterChain.doFilter(wrappedRequest, wrappedResponse);
        } finally {
            long duration = System.currentTimeMillis() - startTime;

            // Log BEFORE copyBodyToResponse — the buffer is still intact here
            logExchange(wrappedRequest, wrappedResponse, duration);

            // Flush the cached response bytes back to the actual output stream
            wrappedResponse.copyBodyToResponse();

            MDC.remove(MDC_TRACE_ID);
            MDC.remove(MDC_CORRELATION_ID);
        }
    }

    private void logExchange(ContentCachingRequestWrapper  request,
                             ContentCachingResponseWrapper response,
                             long                          duration) {

        Map<String, Object> fields = new LinkedHashMap<>();
        fields.put("event.kind",       "inbound");
        fields.put("http.method",      request.getMethod());
        fields.put("http.url",         request.getRequestURI());
        fields.put("http.query",       request.getQueryString() != null ? request.getQueryString() : "");
        fields.put("http.status_code", response.getStatus());
        fields.put("event.duration",   duration);
       // fields.put("client.ip",        resolveClientIp(request));

//        extractRequestHeaders(request)
//                .forEach((k, v) -> fields.put("http.request.headers."  + k, v));
//
//        extractResponseHeaders(response)
//                .forEach((k, v) -> fields.put("http.response.headers." + k, v));
//
//        if (props.isLogBody()) {
//            addBodies(request, response, fields);
//        }

       //log.info(appendEntries(fields),
        log.info("HTTP {} {} -> {} ({}ms)",
                request.getMethod(), request.getRequestURI(),
                response.getStatus(), duration);
    }

}
