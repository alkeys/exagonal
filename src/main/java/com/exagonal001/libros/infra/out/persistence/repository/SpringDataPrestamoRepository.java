package com.exagonal001.libros.infra.out.persistence.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.exagonal001.libros.infra.out.persistence.entity.PrestamosEntity;



public interface SpringDataPrestamoRepository extends JpaRepository<PrestamosEntity, UUID> {

}
