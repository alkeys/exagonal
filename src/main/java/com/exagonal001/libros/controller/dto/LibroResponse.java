package com.exagonal001.libros.controller.dto;

public record LibroResponse(
    String id,
    String titulo,
    String autor,
    int anio
) {
    
}
