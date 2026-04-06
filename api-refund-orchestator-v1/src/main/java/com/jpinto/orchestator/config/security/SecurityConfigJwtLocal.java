package com.jpinto.orchestator.config.security;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtTimestampValidator;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.time.Duration;

/**
 * Configuración de Spring Security activa únicamente con el perfil "jwt-local".
 * <p>
 * Flujo de autenticación en este perfil:
 * 1. El cliente llama al api-auth-service-v1 con sus credenciales → recibe un JWT firmado con HS256
 * 2. El cliente incluye ese JWT en cada request: Authorization: Bearer <token>
 * 3. Este orquestador valida el JWT LOCALMENTE usando la misma clave secreta con la que fue firmado
 * 4. Si el JWT es válido → el request llega al controller
 * Si el JWT es inválido, expiró o no viene → Spring Security retorna 401 automáticamente
 * <p>
 * Ventaja frente a llamar a /validate del auth-service:
 * La validación local evita una llamada HTTP extra por cada request, eliminando latencia
 * y el riesgo de que el auth-service sea un punto único de fallo en cada operación.
 */
@Configuration
//@Profile("jwt-local")
public class SecurityConfigJwtLocal {

    @Bean
    public JwtDecoder jwtDecoder(@Value("${jwt.secret}") String secret) {
        SecretKeySpec secretKey = new SecretKeySpec(
                secret.getBytes(StandardCharsets.UTF_8),
                "HmacSHA256"
        );

        JwtTimestampValidator timestampValidator =
                new JwtTimestampValidator(Duration.ofSeconds(0)); // Sin tolerancia de reloj (clock skew)

        NimbusJwtDecoder decoder = NimbusJwtDecoder.withSecretKey(secretKey).build();
        decoder.setJwtValidator(timestampValidator);

        return decoder;
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter grantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();

        // Lee el claim "roles" del JWT en lugar del claim "scope"/"scp" por defecto
        grantedAuthoritiesConverter.setAuthoritiesClaimName("roles"); // El claim del JWT que contiene los roles

        // Sin prefijo adicional: los roles ya vienen con "ROLE_" desde el auth-service
        // Si tus roles en BD no tienen "ROLE_" (ej: "ADMIN"), cambia esto a "ROLE_"
        // y usa @PreAuthorize("hasAuthority('ROLE_ADMIN')") o @PreAuthorize("hasRole('ADMIN')")
        grantedAuthoritiesConverter.setAuthorityPrefix(StringUtils.EMPTY);

        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(grantedAuthoritiesConverter);
        return converter;
    }

    /**
     * SecurityFilterChain: define la cadena de filtros de seguridad HTTP.
     *
     * Spring Security actúa como "portero" ANTES de que el request llegue a cualquier @RestController.
     * Cada request entrante pasa por esta cadena. Si algún filtro lo rechaza, se retorna
     * 401/403 sin que el código de negocio se ejecute en absoluto.
     */
    @Bean
    public SecurityFilterChain filterChain(
            HttpSecurity http,
            JwtDecoder jwtDecoder,
            JwtAuthenticationConverter jwtAuthenticationConverter) throws Exception {
        http
                // --- CSRF (Cross-Site Request Forgery) ---
                // Se deshabilita porque esta es una API REST stateless consumida por otros microservicios.
                // CSRF protege aplicaciones web con sesiones de browser y cookies de sesión.
                // Como aquí no hay sesiones ni formularios web, CSRF no aplica y solo agregaría overhead.
                .csrf(AbstractHttpConfigurer::disable)

                // --- Gestión de sesión ---
                // STATELESS: Spring Security NO crea ni usa HttpSession.
                // En APIs REST con JWT, cada request es autónomo y lleva su propia autenticación en el header.
                // Crear sesiones sería un desperdicio de recursos y rompería el escalado horizontal
                // (distintas instancias no comparten sesión en memoria).
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                // --- Reglas de autorización por ruta ---
                // Las reglas se evalúan EN ORDEN: la primera que coincide con la ruta gana.
                .authorizeHttpRequests(auth -> auth

                        // /actuator/**: endpoints de Spring Boot Actuator (health, metrics, prometheus).
                        // Se permiten sin token para que load balancers, Kubernetes liveness probes
                        // y Prometheus puedan consultarlos sin autenticarse.
                        // Estos endpoints no exponen datos de negocio sensibles.
                        .requestMatchers("/actuator/**").permitAll()

                        // Cualquier otra ruta requiere un JWT válido en el header Authorization.
                        // Escenarios:
                        //   - No viene header Authorization              → 401 Unauthorized
                        //   - El JWT está expirado o tiene firma inválida → 401 Unauthorized
                        //   - El JWT es válido pero le falta un rol (@PreAuthorize) → 403 Forbidden
                        .anyRequest().authenticated()
                )

                // --- OAuth2 Resource Server ---
                // Activa el modo "servidor de recursos" de Spring Security.
                // Registra internamente el BearerTokenAuthenticationFilter en la cadena.
                //
                // Flujo de ese filtro en cada request:
                //   1. Extrae el token del header: Authorization: Bearer <token>
                //   2. Llama a JwtAuthenticationProvider que usa nuestro JwtDecoder para validarlo
                //   3. Si es válido: crea un JwtAuthenticationToken y lo guarda en SecurityContextHolder
                //      → ese token lo usan luego los interceptores de propagación para reenviarlo downstream
                //   4. Si es inválido: retorna 401 directamente, el request no llega al controller
                .oauth2ResourceServer(oauth2 -> oauth2
                        // Indicamos que los tokens son JWT estructurados (no tokens opacos que requieren
                        // una llamada de introspección remota al auth server para cada request).
                        .jwt(jwt -> jwt
                                // Inyectamos nuestro decoder HMAC personalizado en lugar del default,
                                // que esperaría RS256 configurado vía spring.security.oauth2.resourceserver.jwt.issuer-uri
                                .decoder(jwtDecoder)
                                .jwtAuthenticationConverter(jwtAuthenticationConverter)
                        )
                );

        return http.build();
    }
}
