package com.exagonal001.autores.domain.models.values;

import lombok.Value;

@Value
public class Apellido {
    private final String apellido;
    public Apellido(String apellido) {
        if(apellido == null || apellido.isBlank()) {
            throw new IllegalArgumentException("El apellido no puede ser nulo o vacío");
        }

        this.apellido = apellido.toUpperCase();
    }
}
