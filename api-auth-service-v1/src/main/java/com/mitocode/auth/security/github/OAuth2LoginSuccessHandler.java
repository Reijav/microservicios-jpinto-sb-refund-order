package com.mitocode.auth.security.github;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mitocode.auth.controller.dto.TokenResponse;
import com.mitocode.auth.entity.UserEntity;
import com.mitocode.auth.repository.UserRepository;
import com.mitocode.auth.util.JwtUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Slf4j
@Component
@Profile("oauth2-github")
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

    private static final String PROVIDER = "github";

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final ObjectMapper objectMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

        String username = oAuth2User.getAttribute("login");

        // GitHub puede devolver email null si el usuario lo tiene configurado como privado.
        // En ese caso construimos un email derivado del username para no dejar el campo vacío.
        String email = oAuth2User.getAttribute("email");
        if (email == null) {
            email = username + "@github.com";
            log.warn("GitHub email is private for user '{}', using fallback email", username);
        }

        String finalEmail = email;
        UserEntity user = userRepository.findByUsernameAndProvider(username, PROVIDER)
                .orElseGet(() -> {
                    log.info("Creating new user for GitHub login: {}", username);
                    return userRepository.save(
                            UserEntity.builder()
                                    .username(username)
                                    .email(finalEmail)
                                    .provider(PROVIDER)
                                    .roles(List.of("ROLE_USER"))
                                    .build()
                    );
                });

        user.setEmail(finalEmail);
        userRepository.save(user);

        // Generar nuestro JWT con los mismos roles que el flujo local.
        // El orquestador lo valida sin saber si el usuario se autenticó con GitHub o con /login.
        String accessToken = jwtUtil.generateToken(username, user.getRoles());
        String refreshToken = jwtUtil.generateRefreshToken(username);

        TokenResponse tokenResponse = new TokenResponse(
                accessToken,
                refreshToken,
                jwtUtil.getJwtExpiration().toMillis()
        );

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().write(objectMapper.writeValueAsString(tokenResponse));
    }
}
