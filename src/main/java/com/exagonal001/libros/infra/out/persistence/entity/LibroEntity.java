package com.exagonal001.libros.infra.out.persistence.entity;

import java.util.UUID;

import com.exagonal001.autores.infra.adapter.out.persistence.entity.AutoresEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
    @ManyToOne
    @JoinColumn(name = "id_autor", referencedColumnName = "id")
    private AutoresEntity idautor;
    private int anioPublicacion;
    private String url;
    private int cantidadDisponible;
    
}
