package com.exagonal001.user.application.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.exagonal001.user.application.port.in.rol.ActivoRolCase;
import com.exagonal001.user.application.port.in.rol.CreateRolCase;
import com.exagonal001.user.application.port.in.rol.DeleteRolCase;
import com.exagonal001.user.application.port.in.rol.GetAllRolCase;
import com.exagonal001.user.application.port.in.rol.GetRolCase;
import com.exagonal001.user.application.port.in.rol.UpdateRolCase;
import com.exagonal001.user.application.port.out.RolRepositoryPort;
import com.exagonal001.user.domain.models.Rol;

@Service
public class RolService implements CreateRolCase, GetAllRolCase, ActivoRolCase, UpdateRolCase, GetRolCase, DeleteRolCase {
    private final RolRepositoryPort rolRepositoryPort;

    public RolService(RolRepositoryPort rolRepositoryPort) {
        this.rolRepositoryPort = rolRepositoryPort;
    }

    @Override
    public Rol createRol(Rol rol) {
        return rolRepositoryPort.save(rol);
    }


    @Override
    public List<Rol> getAllRoles() {
        return rolRepositoryPort.getAllRoles();
    }

    @Override
    public boolean setActivoRol(String id, boolean activo) {
        return  rolRepositoryPort.setActivoRol(id, activo);
    }

    @Override
    public void updateRol(String id, Rol rol) {
        rolRepositoryPort.updateRol(id, rol);
    }

    @Override
    public Rol getRolById(String id) {
        return rolRepositoryPort.findById(UUID.fromString(id));
    }

    @Override
    public void deleteRol(String id) {
        rolRepositoryPort.deleteRol(UUID.fromString(id));
    }

    public boolean existsByName(String name) {
        return rolRepositoryPort.existsByName(name);
    }


}