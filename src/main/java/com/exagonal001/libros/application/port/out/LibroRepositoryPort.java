package com.exagonal001.libros.application.port.out;

import java.util.List;

import com.exagonal001.libros.controller.dto.LibroRequest;
import com.exagonal001.libros.controller.dto.LibroResponse;
import com.exagonal001.libros.domain.models.Libro;

public interface LibroRepositoryPort {
    LibroResponse save(LibroRequest libro);
    List<LibroResponse> findAll();
    LibroResponse findById(String id);
}