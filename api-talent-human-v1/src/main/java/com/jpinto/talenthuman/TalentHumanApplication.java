package com.jpinto.talenthuman;

import com.jpinto.talenthuman.infraestructure.persistence.entity.EmployeeJpaEntity;
import com.jpinto.talenthuman.infraestructure.persistence.jpa.EmployeeJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Example;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

@SpringBootApplication
public class TalentHumanApplication {

    @Autowired
    private PlatformTransactionManager transactionManager;


    public static void main(String[] args) {
        SpringApplication.run(TalentHumanApplication.class, args);
    }

    @Bean
    @Profile("dev")
    @Transactional
    public CommandLineRunner demo(EmployeeJpaRepository employeeJpaRepository) {

        return (args) -> {
            TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
            transactionTemplate.execute(status -> {

                if (!employeeJpaRepository.exists(Example.of(new EmployeeJpaEntity()))) {

                    EmployeeJpaEntity contador = EmployeeJpaEntity.builder()
                            .userId(1L)
                            .accountNumber("7895465456456")
                            .bank("ProducBanco")
                            .email("msoliz@jpinto.com")
                            .fullName("Maria Magadalena Soliz Solano")
                            .position("Contador")
                            .build();
                    employeeJpaRepository.save(contador);

                    EmployeeJpaEntity supervisor = EmployeeJpaEntity.builder()
                            .userId(2L)
                            .accountNumber("005548945654")
                            .bank("ProducBanco")
                            .email("fmoreno@jpinto.com")
                            .fullName("Franklin Moreno Moreno")
                            .position("Gerente General")
                            .build();
                    employeeJpaRepository.save(supervisor);

                    EmployeeJpaEntity empleado1 = EmployeeJpaEntity.builder()
                            .userId(3L)
                            .immediateSupervisorId(supervisor.getId())
                            .accountNumber("40221878920")
                            .bank("ProducBanco")
                            .email("lparedez@jpinto.com")
                            .fullName("Lorena Alejandra Paredez Meza")
                            .position("Gerente RRHH")
                            .build();
                    employeeJpaRepository.save(empleado1);


                    EmployeeJpaEntity empleado2 = EmployeeJpaEntity.builder()
                            .userId(4L)
                            .immediateSupervisorId(supervisor.getId())
                            .accountNumber("126548456465")
                            .bank("ProducBanco")
                            .email("landrade@jpinto.com")
                            .fullName("Luis Cristian Andrade Mera")
                            .position("Gerente Comercial")
                            .build();
                    employeeJpaRepository.save(empleado2);


                    EmployeeJpaEntity empleado3 = EmployeeJpaEntity.builder()
                            .userId(5L)
                            .immediateSupervisorId(supervisor.getId())
                            .accountNumber("00985465456")
                            .bank("ProducBanco")
                            .email("jzaldumbide@jpinto.com")
                            .fullName("Juan Marcelo Zaldumbide")
                            .position("Asistente Comercial")
                            .build();
                    employeeJpaRepository.save(empleado3);

                    supervisor.setImmediateSupervisorId(empleado1.getId());
                    employeeJpaRepository.save(supervisor);

                    contador.setImmediateSupervisorId(supervisor.getId());

                }
                return true;

            });

        };
    }
}
