package com.exagonal001.libros.infra.models;

import java.time.LocalDate;
import java.util.UUID;

import com.exagonal001.user.infra.models.UserEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "Prestamos")
public class PrestamosEntity {
    @Id
    @GeneratedValue
    private UUID idPrestamo;


    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity idUsuario;

    @ManyToOne
    @JoinColumn(name = "id_libro")
    private LibroEntity idLibro;

    @Column(name = "fecha_prestamo")
    private LocalDate fechaPrestamo;
    @Column(name = "fecha_devolucion")
    private LocalDate fechaDevolucion;
    
}
