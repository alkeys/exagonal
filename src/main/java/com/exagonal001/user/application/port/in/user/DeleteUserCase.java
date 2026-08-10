package com.exagonal001.user.application.port.in.user;

/**
 * Caso de uso para eliminar usuarios.
 */
public interface DeleteUserCase {

    /**
     * Elimina un usuario por su identificador.
     *
     * @param id identificador del usuario a eliminar
     */
    void deleteUser(String id);
}
