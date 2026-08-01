package com.exagonal001.user.application.port.in;

import java.util.Optional;

import com.exagonal001.user.controller.dto.UserResponse;
import com.exagonal001.user.domain.models.User;

/**
 * Caso de uso para crear usuarios.
 */
public interface CreateUserCase {

    /**
     * Crea un usuario nuevo.
     *
     * @param user usuario con los datos a guardar
     * @return usuario creado
     */
    Optional<User> createUser(User user);
}