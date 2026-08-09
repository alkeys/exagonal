package com.exagonal001.user.controller.dto.User;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * DTO de entrada para crear un usuario desde la API.
 *
 * @param nombre nombre recibido en el request
 * @param apellido apellido recibido en el request
 * @param email email recibido en el request
 */
@Schema(description = "Datos necesarios para registrar un usuario")
public record UserRequest(
    @Schema(description = "Nombre del usuario", example = "Alex")
    String nombre,
    @Schema(description = "Apellido del usuario", example = "Aviles")
    String apellido,
    @Schema(description = "Correo electronico del usuario", example = "alex@example.com")
    String email,
    @Schema(description = "Rol asignado al usuario", example = "USER")
    String rol,
    @Schema(description = "Contraseña del usuario", example = "P@ssw0rd123")
    String password
) {
}