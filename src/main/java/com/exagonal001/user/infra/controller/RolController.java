package com.exagonal001.user.infra.controller;

import java.sql.Date;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exagonal001.user.application.port.in.rol.CreateRolCase;
import com.exagonal001.user.application.port.in.rol.GetAllRolCase;
import com.exagonal001.user.controller.dto.Rol.RolRequest;
import com.exagonal001.user.controller.dto.Rol.RolResponse;
import com.exagonal001.user.domain.models.Rol;
import com.exagonal001.user.domain.models.values.DescripcionRol;
import com.exagonal001.user.domain.models.values.NombreRol;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/rol")
@Tag(name = "Roles", description = "Operaciones para la gestion de roles")
@PreAuthorize("hasRole('ADMIN')")
public class RolController {
    

    private final CreateRolCase createRolCase;

    private final GetAllRolCase getAllRolCase;

    public RolController(CreateRolCase createRolCase, GetAllRolCase getAllRolCase) {
        this.createRolCase = createRolCase;
        this.getAllRolCase = getAllRolCase;
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
        return toDomain(createdRol);
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
                .map(this::toDomain)
                .toList();
    }





    private RolResponse toDomain(Rol rol) {
        return new RolResponse(rol.nombre().getNombre(),
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
