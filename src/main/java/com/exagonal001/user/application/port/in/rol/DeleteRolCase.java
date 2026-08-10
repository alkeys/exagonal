package com.exagonal001.user.application.port.in.rol;

/**
 * Caso de uso para eliminar un rol.
 * DeleteRolCase
 */
public interface DeleteRolCase {

    /**
     * Elimina un rol por su identificador.
     *
     * @param id identificador del rol a eliminar
     */
    void deleteRol(String id);
}
