package com.exagonal001.user.application.port.out;

import java.util.List;
import java.util.UUID;

import com.exagonal001.user.domain.models.Rol;

public interface RolRepositoryPort {

    /**
     * Guarda un rol en el repositorio.
     * @param rol
     * @return
     */
    Rol save(Rol rol);

    /**
     * Obtiene todos los roles del repositorio.
     * @return Lista de roles
     */
    List<Rol> getAllRoles();

    /**
     * Activa o desactiva un rol según el valor de activo.
     * @param id     el identificador del rol
     * @param activo true para activar, false para desactivar
     * @return true si se actualizó correctamente, false en caso contrario
     */
    boolean setActivoRol(String id, boolean activo);

    /**
     * Actualiza el  rol por su identificador podiendo cambiar el nombre y el estado activo etc.
     * @param id identificador del rol
     * @param nombre nombre del rol
     * @param activo true para activar, false para desactivar
     */
    void updateRol(String id, Rol rol);


    boolean existsByName(String name);

    Rol findByName(String name);

    Rol findById(UUID id);

    /**
     * Elimina un rol por su identificador.
     * @param id identificador del rol a eliminar
     */
    void deleteRol(UUID id);

}
