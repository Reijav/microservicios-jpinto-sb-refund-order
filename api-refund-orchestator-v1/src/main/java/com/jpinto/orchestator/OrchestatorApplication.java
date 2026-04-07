package com.jpinto.orchestator;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "API Orquestador de Reembolsos",
                version = "1.0.0",
                description = "Microservicio orquestador que gestiona el ciclo de vida completo de las órdenes de reembolso " +
                        "de empleados. Implementa el patrón SAGA con compensación para garantizar la consistencia " +
                        "distribuida entre los servicios de reembolso, pago, contabilidad y notificaciones.",
                contact = @Contact(
                        name = "Equipo de Desarrollo - J. Pinto",
                        email = "jpinto@empresa.com"
                ),
                license = @License(
                        name = "Uso Interno",
                        url = "https://empresa.com/licencia"
                )
        ),
        servers = {
                @Server(
                        url = "http://localhost:8080",
                        description = "Servidor de desarrollo local"
                ),
        }
)
@SecurityScheme(
        name = "BearerToken",
        description = "Token JWT de autenticación. Ingrese el token en el formato: Bearer {token}",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
@SpringBootApplication
@EnableMethodSecurity
public class OrchestatorApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrchestatorApplication.class, args);
	}

}
