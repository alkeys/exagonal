package com.exagonal001.user.domain.models.values;

import lombok.Value;

@Value
public class DescripcionRol {
    String descripcion;
    public DescripcionRol(String descripcion) {
        if (descripcion == null || descripcion.isBlank()) {
            throw new IllegalArgumentException("La descripción del rol no puede ser nula o vacía");
        }
        this.descripcion = descripcion;
    }
    
}
