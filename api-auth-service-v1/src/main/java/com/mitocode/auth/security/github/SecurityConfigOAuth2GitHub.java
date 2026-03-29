package com.mitocode.auth.security.github;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@Profile("oauth2-github")
public class SecurityConfigOAuth2GitHub {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http,
                                           OAuth2LoginSuccessHandler successHandler) throws Exception {
        return http
                // --- CSRF (Cross-Site Request Forgery) ---
                // Deshabilitado: esta es una API REST stateless, no una aplicación web con formularios.
                // CSRF protege aplicaciones que usan cookies de sesión con formularios HTML.
                // Como aquí no hay sesiones ni formularios, CSRF no aplica y agregaría overhead innecesario.
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll()
                ).oauth2Login(oauth2 ->
                        oauth2.successHandler(successHandler)
                ).build();
    }
}
