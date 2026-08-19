package com.exagonal001.user.infra.adapter.in.web;

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
import com.exagonal001.user.domain.models.Rol;
import com.exagonal001.user.domain.models.values.DescripcionRol;
import com.exagonal001.user.domain.models.values.NombreRol;
import com.exagonal001.user.infra.adapter.in.web.dto.Rol.RolRequest;
import com.exagonal001.user.infra.adapter.in.web.dto.Rol.RolResponse;

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


    @Operation(summary = "Crear un rol", description = "Registra un nuevo rol en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Rol creado correctamente",
                    content = @Content(schema = @Schema(implementation = RolResponse.class))),
            @ApiResponse(responseCode = "400", description = "Datos de entrada invalidos", content = @Content())
    })
    @PostMapping
    public RolResponse CreateRol(@RequestBody RolRequest rolRequest) {
        Rol rol = toDomain(rolRequest);
        Rol createdRol = createRolCase.createRol(rol);
        return toResponse(createdRol);
    }


    @Operation(summary = "Listar roles", description = "Obtiene la lista de todos los roles existentes en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de roles obtenida correctamente")
    })
    @GetMapping
    public List<RolResponse> getAllRoles() {
        List<Rol> roles = getAllRolCase.getAllRoles();
        return roles.stream()
                .map(this::toResponse)
                .toList();
    }

    @Operation(summary = "Obtener un rol por ID", description = "Busca un rol segun su identificador")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Rol encontrado",
                    content = @Content(schema = @Schema(implementation = RolResponse.class))),
            @ApiResponse(responseCode = "404", description = "Rol no encontrado", content = @Content())
    })
    @GetMapping("/{id}")
    public RolResponse getRolById(@Parameter(description = "Identificador del rol") @PathVariable String id) {
        return toResponse(getRolCase.getRolById(id));
    }

    @Operation(summary = "Actualizar un rol", description = "Actualiza el nombre, la descripcion y el estado de un rol existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Rol actualizado correctamente"),
            @ApiResponse(responseCode = "404", description = "Rol no encontrado", content = @Content())
    })
    @PutMapping("/{id}")
    public void updateRol(@Parameter(description = "Identificador del rol") @PathVariable String id, @RequestBody RolRequest rolRequest) {
        Rol rol = toDomain(rolRequest);
        updateRolCase.updateRol(id, rol);
    }

    @Operation(summary = "Activar o desactivar un rol", description = "Cambia el estado activo de un rol segun el valor recibido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estado del rol actualizado correctamente"),
            @ApiResponse(responseCode = "404", description = "Rol no encontrado", content = @Content())
    })
    @PatchMapping("/{id}/activo")
    public void setActivoRol(@Parameter(description = "Identificador del rol") @PathVariable String id,
            @Parameter(description = "Nuevo estado activo del rol") @RequestParam boolean activo) {
        activoRolCase.setActivoRol(id, activo);
    }

    @Operation(summary = "Eliminar un rol", description = "Elimina un rol del sistema segun su identificador")
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
