package com.exagonal001.autores.infra.persistencie;

import org.springframework.stereotype.Repository;

import com.exagonal001.autores.application.port.out.AutoresRepositoryPort;
import com.exagonal001.autores.domain.models.Autore;
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

}
