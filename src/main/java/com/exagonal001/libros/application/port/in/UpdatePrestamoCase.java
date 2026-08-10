package com.exagonal001.libros.application.port.in;

import java.time.LocalDate;

import com.exagonal001.libros.domain.models.PrestamosLibros;

public interface UpdatePrestamoCase {
    PrestamosLibros updatePrestamo(String id, LocalDate fechaPrestamo, LocalDate fechaDevolucion);
}
