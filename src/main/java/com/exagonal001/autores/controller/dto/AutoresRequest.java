package com.exagonal001.autores.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Datos necesarios para registrar un autor")
public record AutoresRequest(
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
