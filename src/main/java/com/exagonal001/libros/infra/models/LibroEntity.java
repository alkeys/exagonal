package com.exagonal001.libros.infra.models;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entidad JPA que representa la tabla de libros.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Libros")
public class LibroEntity {
    @Id
    @GeneratedValue 
    UUID id;
    private String titulo;
    private String autor;
    private int anioPublicacion;
    
}
