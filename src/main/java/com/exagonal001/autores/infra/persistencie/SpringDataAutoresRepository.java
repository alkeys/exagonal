package com.exagonal001.autores.infra.persistencie;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.exagonal001.autores.infra.models.AutoresEntity;


public interface SpringDataAutoresRepository extends JpaRepository<AutoresEntity, UUID> {
    
}
