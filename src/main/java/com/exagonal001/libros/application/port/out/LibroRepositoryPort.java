package com.exagonal001.libros.application.port.out;

import com.exagonal001.libros.domain.models.Libro;

public interface LibroRepositoryPort {

    Libro save(Libro libro);
    
}
