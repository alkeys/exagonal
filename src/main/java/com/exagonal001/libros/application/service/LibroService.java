package com.exagonal001.libros.application.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.exagonal001.libros.application.port.in.CreateLibroCase;
import com.exagonal001.libros.application.port.in.GetAllLibroCase;
import com.exagonal001.libros.application.port.in.GetByidLibreCase;
import com.exagonal001.libros.application.port.out.LibroRepositoryPort;
import com.exagonal001.libros.controller.dto.LibroRequest;
import com.exagonal001.libros.controller.dto.LibroResponse;
import com.exagonal001.libros.domain.models.AnioPublicacion;
import com.exagonal001.libros.domain.models.Libro;

@Service
public class LibroService implements CreateLibroCase , GetAllLibroCase, GetByidLibreCase {

    private final LibroRepositoryPort libroRepositoryPort;

    public LibroService(LibroRepositoryPort libroRepositoryPort) {
        this.libroRepositoryPort = libroRepositoryPort;
    }

    @Override
    public LibroResponse createLibro(LibroRequest libro) {
        Libro savedLibro = libroRepositoryPort.save(toDomain(libro));
        return toResponse(savedLibro);
    }

    @Override
    public List<LibroResponse> getAllLibros(){
        return libroRepositoryPort.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public LibroResponse getById(String id) {
        return toResponse(libroRepositoryPort.findById(id));
    }

    private Libro toDomain(LibroRequest libro) {
        return new Libro(
                null,
                libro.titulo(),
                libro.autor(),
                new AnioPublicacion(libro.anio()),
                libro.url()
        );
    }

    private LibroResponse toResponse(Libro libro) {
        return new LibroResponse(
                libro.id() != null ? libro.id().toString() : null,
                libro.titulo(),
                libro.Idautor(),
            libro.anio().getAnio(),
                libro.url()
        );
    }
}