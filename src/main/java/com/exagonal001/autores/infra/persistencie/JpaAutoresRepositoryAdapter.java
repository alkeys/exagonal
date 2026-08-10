package com.exagonal001.autores.infra.persistencie;

import java.util.List;
import java.util.UUID;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;

import com.exagonal001.autores.application.port.out.AutoresRepositoryPort;
import com.exagonal001.autores.domain.models.Autore;
import com.exagonal001.autores.domain.models.values.Apellido;
import com.exagonal001.autores.domain.models.values.Fecha;
import com.exagonal001.autores.domain.models.values.Nacionalidad;
import com.exagonal001.autores.domain.models.values.Nombre;
import com.exagonal001.autores.infra.models.AutoresEntity;

@Repository
public class JpaAutoresRepositoryAdapter implements AutoresRepositoryPort {
    
    private final SpringDataAutoresRepository springDataAutoresRepository;

    public JpaAutoresRepositoryAdapter(SpringDataAutoresRepository springDataAutoresRepository) {
        this.springDataAutoresRepository = springDataAutoresRepository;
    }

    @Override
    public Autore save(Autore autores) {
        AutoresEntity autoresEntity = new AutoresEntity();
        autoresEntity.setNombre(autores.nombre().getNombre());
        autoresEntity.setApellido(autores.apellido().getApellido());
        autoresEntity.setNacionalidad(autores.nacionalidad().getNacionalidad());
        autoresEntity.setFechaNacimiento(autores.fechaNacimiento().getFecha());
        autoresEntity.setFechaFallecimiento(autores.fechaFallecimiento().getFecha());
        AutoresEntity savedEntity = springDataAutoresRepository.save(autoresEntity);
        return new Autore(
            savedEntity.getId(),
            autores.nombre(),
            autores.apellido(),
            autores.nacionalidad(),
            autores.fechaNacimiento(),
            autores.fechaFallecimiento()
        );
    }


    @Override
    public List<Autore> findAll() {
        List<AutoresEntity> autoresEntities = springDataAutoresRepository.findAll();
        return autoresEntities.stream()
                .map(entity -> new Autore(
                        entity.getId(),
                        new Nombre( entity.getNombre() ),
                        new Apellido( entity.getApellido() ),
                        new Nacionalidad( entity.getNacionalidad() ),
                        new Fecha( entity.getFechaNacimiento() ),
                        new Fecha( entity.getFechaFallecimiento() )
                ))
                .toList();
    }

    @Override
    public Autore findById(String id) {
        AutoresEntity autoresEntity = springDataAutoresRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new RuntimeException("Autor no encontrado con id: " + id));
        return new Autore(
            autoresEntity.getId(),
            new Nombre(autoresEntity.getNombre()),
            new Apellido(autoresEntity.getApellido()),
            new Nacionalidad(autoresEntity.getNacionalidad()),
            new Fecha(autoresEntity.getFechaNacimiento()),
            new Fecha(autoresEntity.getFechaFallecimiento())
        );
    }

    @Override
    public Autore update(String id, Autore autores) {
        AutoresEntity autoresEntity = springDataAutoresRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new RuntimeException("Autor no encontrado con id: " + id));
        autoresEntity.setNombre(autores.nombre().getNombre());
        autoresEntity.setApellido(autores.apellido().getApellido());
        autoresEntity.setNacionalidad(autores.nacionalidad().getNacionalidad());
        autoresEntity.setFechaNacimiento(autores.fechaNacimiento().getFecha());
        autoresEntity.setFechaFallecimiento(autores.fechaFallecimiento().getFecha());
        AutoresEntity updatedEntity = springDataAutoresRepository.save(autoresEntity);
        return new Autore(
            updatedEntity.getId(),
            autores.nombre(),
            autores.apellido(),
            autores.nacionalidad(),
            autores.fechaNacimiento(),
            autores.fechaFallecimiento()
        );
    }

    @Override
    public void delete(String id) {
        AutoresEntity autoresEntity = springDataAutoresRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new RuntimeException("Autor no encontrado con id: " + id));
        try {
            springDataAutoresRepository.delete(autoresEntity);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("No se puede eliminar el autor: tiene libros asociados", e);
        }
    }
}
