package com.exagonal001.user.infra.adapter.out.persistence.repository;

import java.util.UUID;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.exagonal001.user.infra.adapter.out.persistence.entity.RolEntity;

public interface SpringDataRolRepository extends JpaRepository<RolEntity, UUID> {

    boolean existsByNombre(String nombre);

    Optional<RolEntity> findByNombre(String nombre);
       
}
