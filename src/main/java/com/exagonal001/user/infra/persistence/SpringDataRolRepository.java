package com.exagonal001.user.infra.persistence;

import java.util.UUID;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.exagonal001.user.infra.models.RolEntity;

public interface SpringDataRolRepository extends JpaRepository<RolEntity, UUID> {

    boolean existsByNombre(String nombre);

    Optional<RolEntity> findByNombre(String nombre);
       
}
