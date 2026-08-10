package com.exagonal001.libros.application.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.exagonal001.libros.application.port.in.CreatePrestamoCase;
import com.exagonal001.libros.application.port.in.DeletePrestamoCase;
import com.exagonal001.libros.application.port.in.GetAllPrestamoCase;
import com.exagonal001.libros.application.port.in.GetPrestamoCase;
import com.exagonal001.libros.application.port.in.UpdatePrestamoCase;
import com.exagonal001.libros.application.port.out.PrestamosRepositoryPort;
import com.exagonal001.libros.domain.models.PrestamosLibros;

@Service
public class PrestamosService implements CreatePrestamoCase, GetAllPrestamoCase, GetPrestamoCase, UpdatePrestamoCase, DeletePrestamoCase {
    private final PrestamosRepositoryPort prestamosRepositoryPort;

    public PrestamosService(PrestamosRepositoryPort prestamosRepositoryPort) {
        this.prestamosRepositoryPort = prestamosRepositoryPort;
    }

    @Override
    public PrestamosLibros createPrestamo(PrestamosLibros prestamosLibros) {
        return prestamosRepositoryPort.save(prestamosLibros);
    }

    @Override
    public List<PrestamosLibros> getAllPrestamos() {
        return prestamosRepositoryPort.findAll();
    }

    @Override
    public PrestamosLibros getPrestamoById(String id) {
        return prestamosRepositoryPort.findById(id);
    }

    @Override
    public PrestamosLibros updatePrestamo(String id, LocalDate fechaPrestamo, LocalDate fechaDevolucion) {
        return prestamosRepositoryPort.update(id, fechaPrestamo, fechaDevolucion);
    }

    @Override
    public void deletePrestamo(String id) {
        prestamosRepositoryPort.delete(id);
    }
    
}
