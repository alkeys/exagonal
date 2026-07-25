package com.exagonal001.libros.application.port.in;

import com.exagonal001.libros.domain.models.Libro;

public interface CreateLibroCase {
    Libro createLibro(Libro libro);
}