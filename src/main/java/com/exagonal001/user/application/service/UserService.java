package com.exagonal001.user.application.service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.exagonal001.user.application.port.in.CreateUserCase;
import com.exagonal001.user.application.port.in.GetAllUserCase;
import com.exagonal001.user.application.port.in.GetUserCase;
import com.exagonal001.user.application.port.in.UpdateUserCase;
import com.exagonal001.user.application.port.out.UserRepositoryPort;
import com.exagonal001.user.controller.dto.UserResponse;
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
    private final PasswordEncoder passwordEncoder;

    /**
     * Crea el servicio con el puerto de persistencia requerido.
     *
     * @param userRepositoryPort adaptador encargado de guardar y listar usuarios
     * @param passwordEncoder encargado de cifrar la contraseña antes de persistirla
     */
    public UserService(UserRepositoryPort userRepositoryPort, PasswordEncoder passwordEncoder) {
        this.userRepositoryPort = userRepositoryPort;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Guarda un usuario usando el puerto de persistencia.
     *
     * @param user usuario a crear
     * @return usuario persistido, si la operación fue exitosa
     */
    @Override
    public Optional<UserResponse> createUser(User user) {
        String encodedPassword = passwordEncoder.encode(user.password());
        User userWithEncodedPassword = new User(user.id(), user.nombre(), user.apellido(), user.email(), user.rol(), encodedPassword);
        UserResponse savedUser = userRepositoryPort.save(userWithEncodedPassword).orElse(null);
        return Optional.ofNullable(savedUser);
    }

    /**
     * Recupera todos los usuarios registrados.
     *
     * @return lista de usuarios existentes
     */
    @Override
    public List<UserResponse> getAllUsers() {
        return userRepositoryPort.getAllUsers();
    }


    /**
     * Recupera un usuario por su identificador.
     *
     * @param id identificador del usuario
     * @return usuario encontrado o Optional.empty() si no se encuentra
     */
    @Override
    public UserResponse getUserById(String id) {
        UserResponse user2 = userRepositoryPort.getUserById(id);
        if (user2 != null) {
            return new UserResponse(user2.id(), user2.nombre(), user2.apellido(),user2.email(), user2.rol());
        }
        return null;
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


    public boolean existsByRol(String rol) {
        return userRepositoryPort.existsByRol(rol);
    }
}