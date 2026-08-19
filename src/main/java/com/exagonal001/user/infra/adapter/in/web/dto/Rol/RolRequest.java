package com.exagonal001.user.infra.adapter.in.web.dto.Rol;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Datos necesarios para crear o actualizar un rol")
public record RolRequest(
    @Schema(description = "Nombre del rol", example = "ADMIN")
    String nombre,
    @Schema(description = "Descripcion del rol", example = "Rol de administrador")
    String descripcion,
    @Schema(description = "Indica si el rol esta activo", example = "true")
    Boolean activo
) {
    
}
