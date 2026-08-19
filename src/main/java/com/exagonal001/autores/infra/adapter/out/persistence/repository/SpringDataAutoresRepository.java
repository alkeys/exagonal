package com.exagonal001.autores.infra.adapter.out.persistence.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.exagonal001.autores.infra.adapter.out.persistence.entity.AutoresEntity;


public interface SpringDataAutoresRepository extends JpaRepository<AutoresEntity, UUID> {
    
}
