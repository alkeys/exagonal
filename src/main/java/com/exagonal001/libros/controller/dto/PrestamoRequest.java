package com.exagonal001.libros.controller.dto;

public record PrestamoRequest(
    String idUsuario,
    String idLibro,
    String fechaPrestamo,
    String fechaDevolucion
) {
    public PrestamoRequest {
        if (idUsuario == null || idUsuario.isEmpty()) {
            throw new IllegalArgumentException("El ID del usuario no puede ser nulo o vacío");
        }
        if (fechaPrestamo == null || fechaPrestamo.isEmpty()) {
            throw new IllegalArgumentException("La fecha de préstamo no puede ser nula o vacía");
        }
        if (fechaDevolucion == null || fechaDevolucion.isEmpty()) {
            throw new IllegalArgumentException("La fecha de devolución no puede ser nula o vacía");
        }
    }
    
}
