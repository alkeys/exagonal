package com.exagonal001.libros.application.port.in;

import java.util.List;

import com.exagonal001.libros.domain.models.PrestamosLibros;

public interface GetAllPrestamoCase {
    List<PrestamosLibros> getAllPrestamos();
}
