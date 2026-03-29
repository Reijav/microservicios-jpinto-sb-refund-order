package com.jpinto.talenthuman.infraestructure.persistence.jpa;

import com.jpinto.talenthuman.infraestructure.persistence.entity.EmployeeJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployeeJpaRepository extends JpaRepository<EmployeeJpaEntity, Long> {

    Optional<EmployeeJpaEntity> findByUserId(Long userId);
}
