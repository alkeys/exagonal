package com.exagonal001.user.infra.controller;

import java.sql.Date;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.exagonal001.user.application.port.in.rol.ActivoRolCase;
import com.exagonal001.user.application.port.in.rol.CreateRolCase;
import com.exagonal001.user.application.port.in.rol.DeleteRolCase;
import com.exagonal001.user.application.port.in.rol.GetAllRolCase;
import com.exagonal001.user.application.port.in.rol.GetRolCase;
import com.exagonal001.user.application.port.in.rol.UpdateRolCase;
import com.exagonal001.user.controller.dto.Rol.RolRequest;
import com.exagonal001.user.controller.dto.Rol.RolResponse;
import com.exagonal001.user.domain.models.Rol;
import com.exagonal001.user.domain.models.values.DescripcionRol;
import com.exagonal001.user.domain.models.values.NombreRol;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/rol")
@Tag(name = "Roles", description = "Operaciones para la gestion de roles")
@PreAuthorize("hasRole('ADMIN')")
public class RolController {
    

    private final CreateRolCase createRolCase;
    private final GetAllRolCase getAllRolCase;
    private final GetRolCase getRolCase;
    private final UpdateRolCase updateRolCase;
    private final ActivoRolCase activoRolCase;
    private final DeleteRolCase deleteRolCase;

    public RolController(CreateRolCase createRolCase, GetAllRolCase getAllRolCase, GetRolCase getRolCase, UpdateRolCase updateRolCase, ActivoRolCase activoRolCase, DeleteRolCase deleteRolCase) {
        this.createRolCase = createRolCase;
        this.getAllRolCase = getAllRolCase;
        this.getRolCase = getRolCase;
        this.updateRolCase = updateRolCase;
        this.activoRolCase = activoRolCase;
        this.deleteRolCase = deleteRolCase;
    }


    /**
     * Crear un nuevo rol en el sistema.
     * 
     * @param rolRequest Los datos del rol a crear.
     * @return El rol creado.
     */
    @PostMapping
    public RolResponse CreateRol(@RequestBody RolRequest rolRequest) {
        // Implementación del método para crear un rol
        Rol rol = toDomain(rolRequest);
        Rol createdRol = createRolCase.createRol(rol);
        return toResponse(createdRol);
    }


    /**
     * Obtiene todos los roles existentes en el sistema.
     * @param rol
     * @return
     */
    @GetMapping
    public List<RolResponse> getAllRoles() {
        List<Rol> roles = getAllRolCase.getAllRoles();
        return roles.stream()
                .map(this::toResponse)
                .toList();
    }

    /**
     * Obtiene un rol por su identificador.
     * @param id identificador del rol
     * @return el rol encontrado
     */
    @Operation(summary = "Obtener un rol por ID", description = "Busca un rol según su identificador")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Rol encontrado",
                    content = @Content(schema = @Schema(implementation = RolResponse.class))),
            @ApiResponse(responseCode = "404", description = "Rol no encontrado", content = @Content())
    })
    @GetMapping("/{id}")
    public RolResponse getRolById(@Parameter(description = "Identificador del rol") @PathVariable String id) {
        return toResponse(getRolCase.getRolById(id));
    }

    /**
     * Actualiza un rol existente por su identificador.
     * @param id identificador del rol
     * @param rolRequest los nuevos datos del rol
     */
    @Operation(summary = "Actualizar un rol", description = "Actualiza el nombre, la descripción y el estado de un rol existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Rol actualizado correctamente"),
            @ApiResponse(responseCode = "404", description = "Rol no encontrado", content = @Content())
    })
    @PutMapping("/{id}")
    public void updateRol(@Parameter(description = "Identificador del rol") @PathVariable String id, @RequestBody RolRequest rolRequest) {
        Rol rol = toDomain(rolRequest);
        updateRolCase.updateRol(id, rol);
    }

    /**
     * Activa o desactiva un rol.
     * @param id identificador del rol
     * @param activo true para activar, false para desactivar
     */
    @Operation(summary = "Activar o desactivar un rol", description = "Cambia el estado activo de un rol según el valor recibido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estado del rol actualizado correctamente"),
            @ApiResponse(responseCode = "404", description = "Rol no encontrado", content = @Content())
    })
    @PatchMapping("/{id}/activo")
    public void setActivoRol(@Parameter(description = "Identificador del rol") @PathVariable String id,
            @Parameter(description = "Nuevo estado activo del rol") @RequestParam boolean activo) {
        activoRolCase.setActivoRol(id, activo);
    }

    /**
     * Elimina un rol por su identificador.
     * @param id identificador del rol a eliminar
     */
    @Operation(summary = "Eliminar un rol", description = "Elimina un rol del sistema según su identificador")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Rol eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Rol no encontrado", content = @Content())
    })
    @DeleteMapping("/{id}")
    public void deleteRol(@Parameter(description = "Identificador del rol") @PathVariable String id) {
        deleteRolCase.deleteRol(id);
    }

    private RolResponse toResponse(Rol rol) {
        return new RolResponse(rol.id() != null ? rol.id().toString() : null,
                rol.nombre().getNombre(),
                rol.descripcion().getDescripcion(),
                rol.activo());
    }

    private Rol toDomain(RolRequest rolRequest) {
        NombreRol nombre = new NombreRol(rolRequest.nombre());
        DescripcionRol descripcion = new DescripcionRol(rolRequest.descripcion());
        Date fechaCreacion = new Date(System.currentTimeMillis());

        return new Rol(nombre, descripcion, fechaCreacion, rolRequest.activo());
    }
}
