package com.exagonal001.autores.domain.models.values;

import lombok.Value;

@Value
public class Fecha {
    private final Integer fecha;
    public Fecha(Integer fecha) {
        //la fecha puede ser nula 
        if(fecha != null && fecha < 0) {
            throw new IllegalArgumentException("La fecha no puede ser negativa");
        }

        this.fecha = fecha;
    }
    
}
