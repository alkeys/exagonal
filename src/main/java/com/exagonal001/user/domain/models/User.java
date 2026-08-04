package com.exagonal001.user.domain.models;

import java.util.UUID;

/**
 * Modelo de dominio que representa un usuario dentro del slice.
 *
 * @param id identificador único del usuario
 * @param nombre nombre del usuario
 * @param apellido apellido del usuario
 */
public record User(
  UUID id,
  String nombre,
  String apellido,
  String email,
  UUID rolId,
  String password
) {
  public User {
    if (nombre == null || nombre.isBlank()) {
      throw new IllegalArgumentException("El nombre no puede estar vacío");
    }
    if (apellido == null || apellido.isBlank()) {
      throw new IllegalArgumentException("El apellido no puede estar vacío");
    }
  }
}