package com.exagonal001.user.application.port.in;

import java.util.List;

import com.exagonal001.user.domain.models.User;

/**
 * Caso de uso para listar usuarios.
 */
public interface GetAllUserCase {

    /**
     * Obtiene todos los usuarios registrados.
     *
     * @return lista de usuarios
     */
    List<User> getAllUsers();
}