package com.exagonal001.user.infra.models;

import java.sql.Date;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 
 * RolEntity
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
//Indices de la tabla de roles
@Table(name = "Roles", indexes = {
    @Index(name = "idx_nombre", columnList = "nombre"),
    @Index(name = "idx_descripcion", columnList = "descripcion")
})
public class RolEntity {
    @Id
    @GeneratedValue
    private UUID id;
    @Column(name = "nombre", nullable = false, unique = true)
    private String nombre;
    @Column(name = "descripcion", nullable = false)
    private String descripcion;
    @Column(name = "fecha_creacion", nullable = false)
    private Date fechaCreacion;
    @Column(name = "activo", nullable = false)
    private boolean activo;

    public RolEntity(UUID id) {
        this.id = id;
    }
    
}
