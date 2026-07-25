package com.exagonal001.user.infra.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exagonal001.user.application.port.in.CreateUserCase;
import com.exagonal001.user.application.port.in.GetAllUserCase;
import com.exagonal001.user.application.port.in.GetUserCase;
import com.exagonal001.user.application.port.in.UpdateUserCase;
import com.exagonal001.user.controller.dto.UserRequest;
import com.exagonal001.user.controller.dto.UserResponse;
import com.exagonal001.user.domain.models.User;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * Controlador REST del slice de usuarios.
 * <p>
 * Expone las operaciones de creación y listado de usuarios.
 * </p>
 */
@RestController
@RequestMapping("/user")
public class UserController {

    private final CreateUserCase createUserCase;
    private final GetAllUserCase getAllUserCase;
    private final GetUserCase getUserCase;
    private final UpdateUserCase updateUserCase;
    /**
     * Crea el controlador con los casos de uso necesarios.
     *
     * @param createUserCase caso de uso para crear usuarios
     * @param getAllUserCase caso de uso para listar usuarios
     * @param getUserCase caso de uso para obtener un usuario por su identificador
     * @param updateUserCase caso de uso para actualizar un usuario por su identificador
     */
    public UserController(CreateUserCase createUserCase, GetAllUserCase getAllUserCase, GetUserCase getUserCase, UpdateUserCase updateUserCase) {
        this.createUserCase = createUserCase;
        this.getAllUserCase = getAllUserCase;
        this.getUserCase = getUserCase;
        this.updateUserCase = updateUserCase;
    }

    /**
     * Crea un usuario a partir del cuerpo de la petición.
     *
     * @param userRequest datos de entrada del usuario
     * @return usuario creado
     */
    @PostMapping
    public UserResponse createUser(@RequestBody UserRequest userRequest) {
        final User user = new User(null, userRequest.nombre(), userRequest.apellido());
        final Optional<User> createdUser = createUserCase.createUser(user);
        return createdUser
                .map(value -> new UserResponse(value.id(), value.nombre(), value.apellido()))
                .orElseThrow();
    }

    /**
     * Retorna todos los usuarios registrados.
     *
     * @return lista de respuestas de usuarios
     */
    @GetMapping
    public List<UserResponse> getAllUsers() {
        return getAllUserCase.getAllUsers().stream()
                .map(user -> new UserResponse(user.id(), user.nombre(), user.apellido()))
                .toList();
    }



/**
     * Ejemplo de método que recibe un parámetro de consulta.
     *
     * @param param parámetro de consulta
     * @return una cadena de texto
     */
    @GetMapping("getUserById/{id}")
    public User getMethodName(@RequestParam String param) {
        // Lógica para manejar el parámetro de consulta
        return getUserCase.getUserById(param);
    }


    /**
     * Ejemplo de método para actualizar un usuario.
     *
     * @param id identificador del usuario
     * @param nombre nombre del usuario
     * @param apellido apellido del usuario
     */
    @PutMapping("updateUser/{id}")
    public void updateUser(@PathVariable String id, @RequestParam String nombre, @RequestParam String apellido) {
        updateUserCase.updateUser(id, nombre, apellido);
    }

}