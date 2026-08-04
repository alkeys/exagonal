package com.exagonal001.libros.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record LibroRequest(
    //documentation swagger
    @Schema(description = "Titulo del libro", example = "El Quijote")
    String titulo,
    @Schema(description = "Autor del libro", example = "UUID del autor")
    String autor,
    @Schema(description = "Año de publicación del libro", example = "1605")
    int anio,
    @Schema(description = "URL del libro", example = "https://www.example.com/el-quijote")
    String url
) {
    
}
