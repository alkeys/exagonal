package com.exagonal001.libros.domain.models;

import java.util.UUID;

public record Libro(
    UUID id,
    String titulo,
    String autor,
    AnioPublicacion anio
) {
    public Libro {
        if (titulo == null || titulo.isBlank()) {
            throw new IllegalArgumentException("El título no puede estar vacío");
        }
        if (autor == null || autor.isBlank()) {
            throw new IllegalArgumentException("El autor no puede estar vacío");
        }
        if (anio == null) {
            throw new IllegalArgumentException("El año de publicación es obligatorio");
        }
    }
}
