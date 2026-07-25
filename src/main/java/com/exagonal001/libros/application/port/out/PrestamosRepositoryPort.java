package com.exagonal001.libros.application.port.out;

import com.exagonal001.libros.domain.models.PrestamosLibros;


public interface PrestamosRepositoryPort {
    PrestamosLibros save(PrestamosLibros prestamosLibros);
}