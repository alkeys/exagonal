package com.exagonal001.libros.application.service;

import org.springframework.stereotype.Service;

import com.exagonal001.libros.application.port.in.CreatePrestamoCase;
import com.exagonal001.libros.application.port.out.PrestamosRepositoryPort;
import com.exagonal001.libros.domain.models.PrestamosLibros;

@Service
public class PrestamosService implements CreatePrestamoCase {
    private final PrestamosRepositoryPort prestamosRepositoryPort;

    public PrestamosService(PrestamosRepositoryPort prestamosRepositoryPort) {
        this.prestamosRepositoryPort = prestamosRepositoryPort;
    }

    @Override
    public PrestamosLibros createPrestamo(PrestamosLibros prestamosLibros) {
        return prestamosRepositoryPort.save(prestamosLibros);
    }
    
}
