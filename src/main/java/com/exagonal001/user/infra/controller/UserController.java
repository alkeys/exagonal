package com.exagonal001.user.infra.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.access.prepost.PreAuthorize;

import com.exagonal001.user.application.port.in.user.CreateUserCase;
import com.exagonal001.user.application.port.in.user.DeleteUserCase;
import com.exagonal001.user.application.port.in.user.GetAllUserCase;
import com.exagonal001.user.application.port.in.user.GetUserCase;
import com.exagonal001.user.application.port.in.user.UpdateUserCase;
import com.exagonal001.user.controller.dto.User.UserRequest;
import com.exagonal001.user.controller.dto.User.UserResponse;
import com.exagonal001.user.domain.models.User;
import org.springframework.web.bind.annotation.RequestParam;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;


/**
 * Controlador REST del slice de usuarios.
 * <p>
 * Expone las operaciones de creación y listado de usuarios.
 * </p>
 */
@RestController
@RequestMapping("/user")
@Tag(name = "Usuarios", description = "Operaciones para la gestion de usuarios")
@PreAuthorize("hasRole('ADMIN')")
public class UserController {

    private final CreateUserCase createUserCase;
    private final GetAllUserCase getAllUserCase;
    private final GetUserCase getUserCase;
    private final UpdateUserCase updateUserCase;
    private final DeleteUserCase deleteUserCase;
    /**
     * Crea el controlador con los casos de uso necesarios.
     *
     * @param createUserCase caso de uso para crear usuarios
     * @param getAllUserCase caso de uso para listar usuarios
     * @param getUserCase caso de uso para obtener un usuario por su identificador
     * @param updateUserCase caso de uso para actualizar un usuario por su identificador
     */
    public UserController(CreateUserCase createUserCase, GetAllUserCase getAllUserCase, GetUserCase getUserCase, UpdateUserCase updateUserCase, DeleteUserCase deleteUserCase) {
        this.createUserCase = createUserCase;
        this.getAllUserCase = getAllUserCase;
        this.getUserCase = getUserCase;
        this.updateUserCase = updateUserCase;
        this.deleteUserCase = deleteUserCase;
    }

    /**
     * Crea un usuario a partir del cuerpo de la petición.
     *
     * @param userRequest datos de entrada del usuario
     * @return usuario creado
     */
    @Operation(summary = "Crear un usuario", description = "Registra un nuevo usuario en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario creado correctamente",
                    content = @Content(schema = @Schema(implementation = UserResponse.class))),
            @ApiResponse(responseCode = "400", description = "Datos de entrada invalidos", content = @Content())
    })
    @PostMapping
    public UserResponse createUser(@RequestBody UserRequest userRequest) {
        User createdUser = createUserCase.createUser(toDomain(userRequest));
        return toResponse(createdUser);
    }

    /**
     * Retorna todos los usuarios registrados.
     *
     * @return lista de respuestas de usuarios
     */
    @Operation(summary = "Listar usuarios", description = "Obtiene la lista de todos los usuarios registrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de usuarios obtenida correctamente",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = UserResponse.class))))
    })
    @GetMapping
    public List<UserResponse> getAllUsers() {
        return getAllUserCase.getAllUsers().stream()
            .map(this::toResponse)
            .toList();
    }



/**
     * Ejemplo de método que recibe un parámetro de consulta.
     *
     * @param param parámetro de consulta
     * @return una cadena de texto
     */
    @Operation(summary = "Obtener un usuario por ID", description = "Busca un usuario segun su identificador")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario encontrado",
                    content = @Content(schema = @Schema(implementation = UserResponse.class))),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado", content = @Content())
    })
    @GetMapping("getUserById/{id}")
    public UserResponse getMethodName(@Parameter(description = "Identificador del usuario") @PathVariable String id) {
        return toResponse(getUserCase.getUserById(id));
    }


    /**
     * Ejemplo de método para actualizar un usuario.
     *
     * @param id identificador del usuario
     * @param nombre nombre del usuario
     * @param apellido apellido del usuario
     */
    @Operation(summary = "Actualizar un usuario", description = "Actualiza el nombre y apellido de un usuario existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario actualizado correctamente"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado", content = @Content())
    })
    @PutMapping("updateUser/{id}")
    public void updateUser(
            @Parameter(description = "Identificador del usuario") @PathVariable String id,
            @Parameter(description = "Nuevo nombre del usuario") @RequestParam String nombre,
            @Parameter(description = "Nuevo apellido del usuario") @RequestParam String apellido) {
        updateUserCase.updateUser(id, nombre, apellido);
    }

    /**
     * Elimina un usuario por su ID.
     *
     * @param id identificador del usuario
     */
    @Operation(summary = "Eliminar un usuario", description = "Elimina un usuario del sistema según su identificador")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado", content = @Content())
    })
    @DeleteMapping("deleteUser/{id}")
    public void deleteUser(@Parameter(description = "Identificador del usuario") @PathVariable String id) {
        deleteUserCase.deleteUser(id);
    }

    private User toDomain(UserRequest request) {
        return new User(null, request.nombre(), request.apellido(), request.email(),
         UUID.fromString(request.rol()), request.password());
    }

    private UserResponse toResponse(User user) {
        return new UserResponse(user.id(), user.nombre(), user.apellido(), user.email(), user.rolId().toString());
    }

}