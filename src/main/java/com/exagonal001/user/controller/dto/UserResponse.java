package com.exagonal001.user.controller.dto;

import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * DTO de salida para exponer usuarios por la API.
 *
 * @param id identificador del usuario
 * @param nombre nombre del usuario
 * @param apellido apellido del usuario
 */
@Schema(description = "Datos de un usuario registrado en el sistema")
public record UserResponse(
    @Schema(description = "Identificador del usuario", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    UUID id,
    @Schema(description = "Nombre del usuario", example = "Alex")
    String nombre,
    @Schema(description = "Apellido del usuario", example = "Aviles")
    String apellido,
    @Schema(description = "Correo electronico del usuario", example = "alex@example.com")
    String email,
    @Schema(description = "Rol asignado al usuario", example = "ADMIN")
    String rol
) {
}