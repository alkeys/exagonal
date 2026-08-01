package com.exagonal001.user.controller.dto;

/**
 * DTO de entrada para crear un usuario desde la API.
 *
 * @param nombre nombre recibido en el request
 * @param apellido apellido recibido en el request
 */
public record UserRequest(
    String nombre,
    String apellido,
    String rol,
    String password
) {
}