package com.exagonal001.libros.infra.out.persistence.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.exagonal001.libros.infra.out.persistence.entity.LibroEntity;



public interface SpringDataLibroRepository extends JpaRepository<LibroEntity, UUID> {
    
}
