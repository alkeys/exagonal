package com.exagonal001.autores.infra.models;

import java.sql.Date;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Autor")
public class AutoresEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) //para que se genere automáticamente el valor del id
    private UUID id;
    private String nombre;
    private String apellido;
    private String nacionalidad;
    private int fechaNacimiento;
    private Integer fechaFallecimiento;
    
}
