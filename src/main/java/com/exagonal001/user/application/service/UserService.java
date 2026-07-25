package com.exagonal001.user.application.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.exagonal001.user.application.port.in.CreateUserCase;
import com.exagonal001.user.application.port.in.GetAllUserCase;
import com.exagonal001.user.application.port.in.GetUserCase;
import com.exagonal001.user.application.port.in.UpdateUserCase;
import com.exagonal001.user.application.port.out.UserRepositoryPort;
import com.exagonal001.user.domain.models.User;

/**
 * Servicio de aplicación para el caso de uso de usuarios.
 * <p>
 * Orquesta la creación y la lectura de usuarios delegando la persistencia al
 * puerto de salida {@link UserRepositoryPort}.
 * </p>
 */
@Service
public class UserService implements CreateUserCase, GetAllUserCase, GetUserCase, UpdateUserCase {

    private final UserRepositoryPort userRepositoryPort;

    /**
     * Crea el servicio con el puerto de persistencia requerido.
     *
     * @param userRepositoryPort adaptador encargado de guardar y listar usuarios
     */
    public UserService(UserRepositoryPort userRepositoryPort) {
        this.userRepositoryPort = userRepositoryPort;
    }

    /**
     * Guarda un usuario usando el puerto de persistencia.
     *
     * @param user usuario a crear
     * @return usuario persistido, si la operación fue exitosa
     */
    @Override
    public Optional<User> createUser(User user) {
        return userRepositoryPort.save(user);
    }

    /**
     * Recupera todos los usuarios registrados.
     *
     * @return lista de usuarios existentes
     */
    @Override
    public List<User> getAllUsers() {
        return userRepositoryPort.getAllUsers();
    }


    /**
     * Recupera un usuario por su identificador.
     *
     * @param id identificador del usuario
     * @return usuario encontrado o Optional.empty() si no se encuentra
     */
    @Override
    public User getUserById(String id) {
        return userRepositoryPort.getUserById(id);
    }

    /**
     * Actualiza un usuario por su identificador.
     *
     * @param id identificador del usuario
     * @param nombre nombre del usuario
     * @param apellido apellido del usuario
     */
    @Override
    public void updateUser(String id, String nombre, String apellido) {
        userRepositoryPort.updateUser(id, nombre, apellido);
    }

}