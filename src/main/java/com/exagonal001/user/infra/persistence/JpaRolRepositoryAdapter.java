package com.exagonal001.user.infra.persistence;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.exagonal001.user.application.port.out.RolRepositoryPort;
import com.exagonal001.user.domain.models.Rol;
import com.exagonal001.user.domain.models.values.DescripcionRol;
import com.exagonal001.user.domain.models.values.NombreRol;
import com.exagonal001.user.infra.models.RolEntity;

@Repository
public class JpaRolRepositoryAdapter implements RolRepositoryPort {

    private final SpringDataRolRepository springDataRolRepository;

    public JpaRolRepositoryAdapter(SpringDataRolRepository springDataRolRepository) {
        this.springDataRolRepository = springDataRolRepository;
    }

    
    @Override
    public Rol save(Rol rol) {
        RolEntity rolEntity = new RolEntity(null, rol.nombre().getNombre(), 
        rol.descripcion().getDescripcion(),rol.fechaCreacion(), rol.activo().booleanValue());
        rolEntity = springDataRolRepository.save(rolEntity);
        return toDomain(rolEntity);
    }

    @Override
    public List<Rol> getAllRoles() {
        List<RolEntity> rolEntities = springDataRolRepository.findAll();
        return rolEntities.stream().map(this::toDomain).toList();
    }

    @Override
    public boolean setActivoRol(String id, boolean activo) {
        return springDataRolRepository.findById(UUID.fromString(id)).map(rolEntity -> {
            rolEntity.setActivo(activo);
            springDataRolRepository.save(rolEntity);
            return true;
        }).orElse(false);
    }

    @Override
    public void updateRol(String id, Rol rol) {
        springDataRolRepository.findById(UUID.fromString(id)).ifPresent(rolEntity -> {
            rolEntity.setNombre(rol.nombre().getNombre());
            rolEntity.setDescripcion(rol.descripcion().getDescripcion());
            rolEntity.setActivo(rol.activo().booleanValue());
            springDataRolRepository.save(rolEntity);
        });
    }

    private Rol toDomain(RolEntity rolEntity) {
        return new Rol(rolEntity.getId(),
                new NombreRol(rolEntity.getNombre()),
                new DescripcionRol(rolEntity.getDescripcion()),
                rolEntity.getFechaCreacion(),
                rolEntity.isActivo());
    }


    @Override
    public boolean existsByName(String name) {
        return springDataRolRepository.existsByNombre(name);    
    }

    @Override
    public Rol findByName(String name) {
        return springDataRolRepository.findByNombre(name)
                .map(this::toDomain)
                .orElseThrow(() -> new IllegalArgumentException("Rol no encontrado: " + name));
    }

    @Override
    public Rol findById(UUID id) {
        return springDataRolRepository.findById(id)
                .map(this::toDomain)
                .orElseThrow(() -> new IllegalArgumentException("Rol no encontrado: " + id));
    }
}
