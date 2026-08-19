package com.exagonal001.libros.infra.in.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record LibroRequest(
    @Schema(description = "Titulo del libro", example = "El Quijote")
    String titulo,
    @Schema(description = "Autor del libro", example = "UUID del autor")
    String autor,
    @Schema(description = "Ano de publicacion del libro", example = "1605")
    int anio,
    @Schema(description = "URL del libro", example = "https://www.example.com/el-quijote")
    String url,
    @Schema(description = "Cantidad de ejemplares disponibles", example = "10")
    int cantidadDisponible
) {
    
}
