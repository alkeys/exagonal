package com.exagonal001.user.application.port.in.rol;

import com.exagonal001.user.domain.models.Rol;

/**
 * Caso de uso para obtener un rol por su identificador.
 * GetRolCase
 */
public interface GetRolCase {

    /**
     * Obtiene un rol por su identificador.
     *
     * @param id identificador del rol
     * @return el rol encontrado
     */
    Rol getRolById(String id);
}
