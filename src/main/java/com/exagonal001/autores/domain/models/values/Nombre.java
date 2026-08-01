package com.exagonal001.autores.domain.models.values;

import lombok.Value;

@Value
public class Nombre {
    private final String nombre;
    public Nombre(String nombre) {
        if(nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre no puede ser nulo o vacío");
        }

        this.nombre = nombre.toUpperCase();
    }
    
}
