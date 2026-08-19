package com.exagonal001.autores.infra.adapter.in.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Datos de un autor registrado en el sistema")
public record AutoresResponse(
    @Schema(description = "Identificador del autor", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    String id,
    @Schema(description = "Nombre del autor", example = "Miguel")
    String nombre,
    @Schema(description = "Apellido del autor", example = "de Cervantes")
    String apellido,
    @Schema(description = "Nacionalidad del autor", example = "Española")
    String nacionalidad,
    @Schema(description = "Año de nacimiento del autor", example = "1547")
    int anioNacimiento,
    @Schema(description = "Año de fallecimiento del autor (opcional si sigue con vida)", example = "1616")
    Integer anioFallecimiento
) {

}
