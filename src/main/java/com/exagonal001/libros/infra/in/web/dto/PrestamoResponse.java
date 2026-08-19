package com.exagonal001.libros.infra.in.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Datos de un prestamo registrado en el sistema")
public record PrestamoResponse(
    @Schema(description = "Identificador del prestamo", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    String idPrestamo,
    @Schema(description = "Identificador del usuario que realizo el prestamo", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    String idUsuario,
    @Schema(description = "Identificador del libro prestado", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    String idLibro,
    @Schema(description = "Estado del prestamo", example = "true")
    boolean estadoPrestamo,
    @Schema(description = "Fecha en que se realizo el prestamo", example = "2026-08-01")
    String fechaPrestamo,
    @Schema(description = "Fecha limite para devolver el libro", example = "2026-08-15")
    String fechaDevolucion
) {

}
