package com.exagonal001.libros.application.port.in;

import com.exagonal001.libros.domain.models.PrestamosLibros;

public interface CreatePrestamoCase {
    PrestamosLibros createPrestamo(PrestamosLibros prestamosLibros);
    
}
