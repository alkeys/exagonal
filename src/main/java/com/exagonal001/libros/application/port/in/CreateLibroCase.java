package com.exagonal001.libros.application.port.in;

import com.exagonal001.libros.controller.dto.LibroRequest;
import com.exagonal001.libros.controller.dto.LibroResponse;
import com.exagonal001.libros.domain.models.Libro;

public interface CreateLibroCase {
    LibroResponse createLibro(LibroRequest libro);
}