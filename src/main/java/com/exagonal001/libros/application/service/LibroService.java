package com.exagonal001.libros.application.service;

import org.springframework.stereotype.Service;

import com.exagonal001.libros.application.port.in.CreateLibroCase;
import com.exagonal001.libros.application.port.out.LibroRepositoryPort;
import com.exagonal001.libros.domain.models.Libro;

@Service
public class LibroService implements CreateLibroCase {

    private final LibroRepositoryPort libroRepositoryPort;

    public LibroService(LibroRepositoryPort libroRepositoryPort) {
        this.libroRepositoryPort = libroRepositoryPort;
    }

    @Override
    public Libro createLibro(Libro libro) {
        return libroRepositoryPort.save(libro);
    }
}