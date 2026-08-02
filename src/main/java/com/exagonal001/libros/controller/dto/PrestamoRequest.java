package com.exagonal001.libros.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Datos necesarios para registrar un prestamo de libro")
public record PrestamoRequest(
    @Schema(description = "Identificador del usuario que solicita el prestamo", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    String idUsuario,
    @Schema(description = "Identificador del libro solicitado", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    String idLibro,
    @Schema(description = "Fecha en que se realiza el prestamo (yyyy-MM-dd)", example = "2026-08-01")
    String fechaPrestamo,
    @Schema(description = "Fecha limite para devolver el libro (yyyy-MM-dd)", example = "2026-08-15")
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
