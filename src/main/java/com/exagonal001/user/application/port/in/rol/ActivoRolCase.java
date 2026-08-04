package com.exagonal001.user.application.port.in.rol;

/**
 * Caso de uso para activar un rol o desactivarlo.
 * ActivoRolCase
 */
public interface ActivoRolCase {
    /**
     * Activa o desactiva un rol según el valor de activo.
     *
     * @param id     el identificador del rol
     * @param activo true para activar, false para desactivar
     * @return true si se actualizó correctamente, false en caso contrario
     */
    boolean setActivoRol(String id, boolean activo);


}
