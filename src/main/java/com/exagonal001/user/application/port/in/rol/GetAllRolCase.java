package com.exagonal001.user.application.port.in.rol;

import java.util.List;

import com.exagonal001.user.domain.models.Rol;

/**
 * retorna los roles que existen en el sistema
 * GetRolCase
 */
public interface GetAllRolCase {
    /**
     * Obtiene todos los roles registrados.
     *
     * @return lista de roles
     */
    List<Rol> getAllRoles();
    
}
