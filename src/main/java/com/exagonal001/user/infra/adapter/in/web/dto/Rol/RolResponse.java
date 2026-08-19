package com.exagonal001.user.infra.adapter.in.web.dto.Rol;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Datos de un rol registrado en el sistema")
public record RolResponse(
    @Schema(description = "Identificador del rol", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    String id,
    @Schema(description = "Nombre del rol", example = "ADMIN")
    String nombre,
    @Schema(description = "Descripcion del rol", example = "Rol de administrador")
    String descripcion,
    @Schema(description = "Indica si el rol esta activo", example = "true")
    boolean activo
) {
    
}
