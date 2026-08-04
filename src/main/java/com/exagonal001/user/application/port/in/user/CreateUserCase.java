package com.exagonal001.user.application.port.in.user;

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
    User createUser(User user);
}