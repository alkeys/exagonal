package com.exagonal001.libros.application.port.out;

import java.util.List;

import com.exagonal001.libros.domain.models.Libro;

public interface LibroRepositoryPort {
    Libro save(Libro libro);
    List<Libro> findAll();
    Libro findById(String id);
}