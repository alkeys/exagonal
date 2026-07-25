package com.exagonal001.libros.controller.dto;

public record LibroRequest(
    String titulo,
    String autor,
    int anio
) {
    
}
