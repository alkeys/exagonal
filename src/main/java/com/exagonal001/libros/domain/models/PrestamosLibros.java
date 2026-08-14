package com.exagonal001.libros.domain.models;

import java.util.UUID;

public record PrestamosLibros(
    UUID idPrestamo,
    UUID idUsuario,    
    UUID idLibro,
    boolean estadoPrestamo,
    java.time.LocalDate fechaPrestamo,
    java.time.LocalDate fechaDevolucion
) {
    public PrestamosLibros {
        if (idUsuario == null) {
            throw new IllegalArgumentException("El id del usuario no puede ser nulo");
        }
        if (idLibro == null) {
            throw new IllegalArgumentException("El id del libro no puede ser nulo");
        }
        if (fechaPrestamo == null) {
            throw new IllegalArgumentException("La fecha de prestamo no puede ser nula");
        }
        if (fechaDevolucion == null) {
            throw new IllegalArgumentException("La fecha de devolucion no puede ser nula");
        }
        if (estadoPrestamo != true && estadoPrestamo != false) {
            throw new IllegalArgumentException("El estado del prestamo debe ser true o false");
        }

    }
    
    
}
