package com.exagonal001.libros.domain.models;

import java.util.UUID;

import com.exagonal001.libros.domain.models.values.AnioPublicacion;
import com.exagonal001.libros.domain.models.values.Cantidad;

public record Libro(
    UUID id,
    String titulo,
    String Idautor,
    AnioPublicacion anio,   
    String url,
    Cantidad cantidadDisponible
    
) {
    public Libro {
        if (titulo == null || titulo.isBlank()) {
            throw new IllegalArgumentException("El título no puede estar vacío");
        }
        if (Idautor == null || Idautor.isBlank()) {
            throw new IllegalArgumentException("El autor no puede estar vacío");
        }
        if (anio == null) {
            throw new IllegalArgumentException("El año de publicación es obligatorio");
        }
        if (cantidadDisponible == null) {
            throw new IllegalArgumentException("La cantidad disponible es obligatoria");
        }
    }
}
