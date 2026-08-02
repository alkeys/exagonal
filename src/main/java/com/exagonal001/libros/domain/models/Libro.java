package com.exagonal001.libros.domain.models;

import java.util.UUID;

public record Libro(
    UUID id,
    String titulo,
    String Idautor,
    AnioPublicacion anio,   
    String url
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
    }
}
