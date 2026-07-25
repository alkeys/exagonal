package com.exagonal001.libros.controller.dto;

public record PrestamoResponse(
    String idPrestamo,
    String idUsuario,
    String idLibro,
    String fechaPrestamo,
    String fechaDevolucion
) {
    
}
