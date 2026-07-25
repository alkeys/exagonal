package com.exagonal001.user.controller.dto;

import java.util.UUID;

/**
 * DTO de salida para exponer usuarios por la API.
 *
 * @param id identificador del usuario
 * @param nombre nombre del usuario
 * @param apellido apellido del usuario
 */
public record UserResponse(
    UUID id,
    String nombre,
    String apellido
) {
}