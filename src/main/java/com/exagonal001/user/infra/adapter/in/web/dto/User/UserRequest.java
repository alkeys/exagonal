package com.exagonal001.user.infra.adapter.in.web.dto.User;

import io.swagger.v3.oas.annotations.media.Schema;

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
    @Schema(description = "Contrasena del usuario", example = "P@ssw0rd123")
    String password
) {
}
