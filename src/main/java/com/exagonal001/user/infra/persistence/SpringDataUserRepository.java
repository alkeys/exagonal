package com.exagonal001.user.infra.persistence;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.exagonal001.user.infra.models.UserEntity;

/**
 * Repositorio Spring Data para la entidad de usuarios.
 */
public interface SpringDataUserRepository extends JpaRepository<UserEntity, UUID> {

    boolean existsByRolNombre(String rol);

    Optional<UserEntity> findByEmail(String email);
    
}