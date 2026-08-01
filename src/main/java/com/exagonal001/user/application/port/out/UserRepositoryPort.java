package com.exagonal001.user.application.port.out;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.exagonal001.user.controller.dto.UserResponse;
import com.exagonal001.user.domain.models.User;

/**
 * Puerto de salida para persistir y consultar usuarios.
 */
public interface UserRepositoryPort {

    /**
     * Guarda un usuario en el almacenamiento.
     *
     * @param user usuario a persistir
     * @return usuario persistido con su identificador asignado
     */
    Optional<UserResponse> save(User user);

    /**
     * Recupera todos los usuarios almacenados.
     *
     * @return lista completa de usuarios
     */
    List<UserResponse> getAllUsers();

    /**
     * Recupera un usuario por su identificador.
     *
     * @param id identificador del usuario
     * @return usuario encontrado o Optional.empty() si no se encuentra
     */
    UserResponse getUserById(String id);

    /**
     * Actualiza un usuario por su identificador.
     *
     * @param id identificador del usuario
     * @param nombre nombre del usuario
     * @param apellido apellido del usuario
     */
    void updateUser(String id, String nombre, String apellido);



    boolean existsByRol(String rol);
}