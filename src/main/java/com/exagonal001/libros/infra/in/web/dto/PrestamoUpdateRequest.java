package com.exagonal001.libros.infra.in.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Datos necesarios para actualizar un prestamo de libro")
public record PrestamoUpdateRequest(
    @Schema(description = "Fecha en que se realiza el prestamo (yyyy-MM-dd)", example = "2026-08-01")
    String fechaPrestamo,
    @Schema(description = "Fecha limite para devolver el libro (yyyy-MM-dd)", example = "2026-08-15")
    String fechaDevolucion
) {
    public PrestamoUpdateRequest {
        if (fechaPrestamo == null || fechaPrestamo.isEmpty()) {
            throw new IllegalArgumentException("La fecha de prestamo no puede ser nula o vacia");
        }
        if (fechaDevolucion == null || fechaDevolucion.isEmpty()) {
            throw new IllegalArgumentException("La fecha de devolucion no puede ser nula o vacia");
        }
    }
}
