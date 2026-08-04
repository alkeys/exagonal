package com.exagonal001.libros.application.port.in;

import java.util.List;

import com.exagonal001.libros.domain.models.Libro;

public interface GetAllLibroCase {
    List<Libro> getAllLibros();
    
}
