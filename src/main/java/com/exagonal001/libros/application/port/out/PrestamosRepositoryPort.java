package com.exagonal001.libros.application.port.out;

import java.time.LocalDate;
import java.util.List;

import com.exagonal001.libros.domain.models.PrestamosLibros;


public interface PrestamosRepositoryPort {
    PrestamosLibros save(PrestamosLibros prestamosLibros);
    List<PrestamosLibros> findAll();
    PrestamosLibros findById(String id);
    PrestamosLibros update(String id, LocalDate fechaPrestamo, LocalDate fechaDevolucion);
    void delete(String id);
    void devolverLibro(String id, String idUsuario);
}