package com.exagonal001.user.controller.dto.Rol;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 
 * RolRequest 
 * a DTO for creating or updating a Rol.
 * @param nombre
 * @param descripcion
 * @param activo
 */
public record RolRequest(
    @Schema(description = "Nombre del rol", example = "ADMIN")
    String nombre,
    @Schema(description = "Descripción del rol", example = "Rol de administrador")
    String descripcion,
    @Schema(description = "Indica si el rol está activo", example = "true")
    Boolean activo
) {
    
}
