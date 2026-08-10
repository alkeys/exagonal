package com.exagonal001.libros.application.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.exagonal001.libros.application.port.in.CreateLibroCase;
import com.exagonal001.libros.application.port.in.DeleteLibroCase;
import com.exagonal001.libros.application.port.in.GetAllLibroCase;
import com.exagonal001.libros.application.port.in.GetByidLibreCase;
import com.exagonal001.libros.application.port.in.UpdateLibroCase;
import com.exagonal001.libros.application.port.out.LibroRepositoryPort;
import com.exagonal001.libros.domain.models.Libro;

@Service
public class LibroService implements CreateLibroCase , GetAllLibroCase, GetByidLibreCase, UpdateLibroCase, DeleteLibroCase {

    private final LibroRepositoryPort libroRepositoryPort;

    public LibroService(LibroRepositoryPort libroRepositoryPort) {
        this.libroRepositoryPort = libroRepositoryPort;
    }

    @Override
    public Libro createLibro(Libro libro) {
        return libroRepositoryPort.save(libro);
    }

    @Override
    public List<Libro> getAllLibros(){
        return libroRepositoryPort.findAll();
    }

    @Override
    public Libro getById(String id) {
        return libroRepositoryPort.findById(id);
    }

    @Override
    public Libro updateLibro(String id, Libro libro) {
        return libroRepositoryPort.update(id, libro);
    }

    @Override
    public void deleteLibro(String id) {
        libroRepositoryPort.delete(id);
    }
}