package com.exagonal001.libros.infra.persistencie;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.exagonal001.libros.infra.models.LibroEntity;

public interface SpringDataLibroRepository extends JpaRepository<LibroEntity, UUID> {
    
}
