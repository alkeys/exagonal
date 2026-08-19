package com.exagonal001.user.infra.adapter.out.persistence.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.exagonal001.user.infra.adapter.out.persistence.entity.UserEntity;

public interface SpringDataUserRepository extends JpaRepository<UserEntity, UUID> {

    boolean existsByRolNombre(String rol);

    Optional<UserEntity> findByEmail(String email);
    
}
