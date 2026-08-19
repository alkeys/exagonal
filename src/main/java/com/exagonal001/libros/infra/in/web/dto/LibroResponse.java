package com.exagonal001.libros.infra.in.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Datos de un libro registrado en el sistema")
public record LibroResponse(
    @Schema(description = "Identificador del libro", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    String id,
    @Schema(description = "Titulo del libro", example = "El Quijote")
    String titulo,
    @Schema(description = "Autor del libro", example = "Miguel de Cervantes")
    String autor,
    @Schema(description = "Ano de publicacion del libro", example = "1605")
    int anio,
    @Schema(description = "URL del libro", example = "https://www.example.com/el-quijote")
    String url,
    @Schema(description = "Cantidad de ejemplares disponibles", example = "10")
    int cantidadDisponible
) {

}
