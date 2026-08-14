package com.exagonal001.libros.domain.models.values;

public class Cantidad {
    private final int cantidad;

    public Cantidad(int cantidad) {
        if (cantidad < 0) {
            throw new IllegalArgumentException("La cantidad no puede ser negativa");
        }
        this.cantidad = cantidad;
    }

    public int getCantidad() {
        return cantidad;
    }
    
}
