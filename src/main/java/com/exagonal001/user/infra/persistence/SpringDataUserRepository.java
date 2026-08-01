package com.exagonal001.user.infra.persistence;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.exagonal001.user.infra.models.UserEntity;

/**
 * Repositorio Spring Data para la entidad de usuarios.
 */
public interface SpringDataUserRepository extends JpaRepository<UserEntity, UUID> {

    boolean existsByRol(String rol);
    
}