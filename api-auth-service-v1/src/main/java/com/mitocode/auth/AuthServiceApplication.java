package com.mitocode.auth;

import com.mitocode.auth.entity.UserEntity;
import com.mitocode.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Example;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;

@Slf4j
@SpringBootApplication
public class AuthServiceApplication {

    @Autowired
    private PlatformTransactionManager transactionManager;

    private TransactionTemplate transactionTemplate;

	public static void main(String[] args) {
		SpringApplication.run(AuthServiceApplication.class, args);
	}


    @Bean
    @Profile("dev")
    public CommandLineRunner demo(UserRepository userRepository, PasswordEncoder encoder) {
        return (args) -> {
            transactionTemplate = new TransactionTemplate(transactionManager);

            transactionTemplate.execute(status -> {

                if (!userRepository.exists(Example.of(new UserEntity()))) {
                    log.info("### INICIALIZANDO DATOS AMBIENTE DESARROLLO ###");

                    UserEntity contador = UserEntity.builder()
                            .roles(List.of("CONTADOR"))
                            .username("contador")
                            .password(encoder.encode("contador123"))
                            .build();
                    userRepository.save(contador);

                    UserEntity supervisor = UserEntity.builder()
                            .roles(List.of("EMPLEADO", "SUPERVISOR"))
                            .username("supervisor")
                            .password(encoder.encode("supervisor123"))
                            .build();
                    userRepository.save(supervisor);

                    UserEntity empleado1 = UserEntity.builder()
                            .roles(List.of("EMPLEADO"))
                            .username("empleado1")
                            .password(encoder.encode("empleado123"))
                            .build();
                    userRepository.save(empleado1);


                    UserEntity empleado2 = UserEntity.builder()
                            .roles(List.of("EMPLEADO"))
                            .username("empleado2")
                            .password(encoder.encode("empleado123"))
                            .build();
                    userRepository.save(empleado2);


                    UserEntity empleado3 = UserEntity.builder()
                            .roles(List.of("EMPLEADO"))
                            .username("empleado3")
                            .password(encoder.encode("empleado123"))
                            .build();
                    userRepository.save(empleado3);
                }
                return true;
            });

        };
    }
}
