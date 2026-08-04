package com.exagonal001.libros.application.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.exagonal001.libros.application.port.in.CreateLibroCase;
import com.exagonal001.libros.application.port.in.GetAllLibroCase;
import com.exagonal001.libros.application.port.in.GetByidLibreCase;
import com.exagonal001.libros.application.port.out.LibroRepositoryPort;
import com.exagonal001.libros.controller.dto.LibroRequest;
import com.exagonal001.libros.controller.dto.LibroResponse;
import com.exagonal001.libros.domain.models.Libro;

@Service
public class LibroService implements CreateLibroCase , GetAllLibroCase, GetByidLibreCase {

    private final LibroRepositoryPort libroRepositoryPort;

    public LibroService(LibroRepositoryPort libroRepositoryPort) {
        this.libroRepositoryPort = libroRepositoryPort;
    }

    @Override
    public LibroResponse createLibro(LibroRequest libro) {
        return libroRepositoryPort.save(libro);
    }

    @Override
    public List<LibroResponse> getAllLibros(){
        return libroRepositoryPort.findAll();
    }

    @Override
    public LibroResponse getById(String id) {
        return libroRepositoryPort.findById(id);
    }
}