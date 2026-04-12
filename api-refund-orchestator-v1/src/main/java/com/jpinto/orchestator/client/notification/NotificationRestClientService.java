package com.jpinto.orchestator.client.notification;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jpinto.orchestator.client.notification.dto.RequestSendMail;
import com.jpinto.orchestator.exceptions.ServicioNotificacionesException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClient;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

@Component
@RequiredArgsConstructor
@Slf4j
public class NotificationRestClientService {
    private final RestClient notificationRestClient;
    private final ObjectMapper objectMapper;

    public Boolean encolarEnvioHtmlMail(RequestSendMail request) {
        log.info("Calling send mail.");

        return notificationRestClient.post().uri("/email/encolar-envio-mail").body(request)
                .retrieve()
                .onStatus(HttpStatusCode::is5xxServerError,
                        (httpRequest, clientHttpResponse) -> {
                            var cadenaError =new String(clientHttpResponse.getBody().readAllBytes(), StandardCharsets.UTF_8);
                            ProblemDetail problemDetail=objectMapper.readValue(cadenaError, ProblemDetail.class);
                            var detalleError = problemDetail.getTitle() +": "+ problemDetail.getDetail() +": " + cadenaError;
                            log.error(detalleError);
                            throw new ServicioNotificacionesException(detalleError);
                        })
                .onStatus(HttpStatusCode::is4xxClientError,
                        (httpRequest, clientHttpResponse) -> {
                            var cadenaError =new String(clientHttpResponse.getBody().readAllBytes(), StandardCharsets.UTF_8);
                            ProblemDetail problemDetail=objectMapper.readValue(cadenaError, ProblemDetail.class);
                            var detalleError = problemDetail.getTitle() +": "+ problemDetail.getDetail() +": " + cadenaError;
                            log.error(detalleError);
                            throw new ServicioNotificacionesException(detalleError);
                        })
//                .onStatus(httpStatusCode -> {
//                    log.error("Error no identificado " + httpStatusCode.getStatusText());
//                    throw new ServicioNotificacionesException("Error no identificado " + httpStatusCode.getStatusText());
//                })
                .body(Boolean.class);

    }
}
