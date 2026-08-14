package com.exagonal001.libros.domain.models.values;

import java.time.Year;

import lombok.Value;

@Value
public class AnioPublicacion {

    private final int anio;

    public AnioPublicacion(int anio) {
        if (anio < 0 || anio > Year.now().getValue()) {
            throw new IllegalArgumentException("El año de publicación no es válido");
        }
        this.anio = anio;
    }

}
