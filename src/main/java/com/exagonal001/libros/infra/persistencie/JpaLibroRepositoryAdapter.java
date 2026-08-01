package com.exagonal001.libros.infra.persistencie;

import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.exagonal001.autores.infra.models.AutoresEntity;
import com.exagonal001.libros.application.port.out.LibroRepositoryPort;
import com.exagonal001.libros.domain.models.AnioPublicacion;
import com.exagonal001.libros.domain.models.Libro;
import com.exagonal001.libros.infra.models.LibroEntity;

@Repository
public class JpaLibroRepositoryAdapter implements LibroRepositoryPort {

    private final SpringDataLibroRepository springDataLibroRepository;

    public JpaLibroRepositoryAdapter(SpringDataLibroRepository springDataLibroRepository) {
        this.springDataLibroRepository = springDataLibroRepository;
    }

    @Override
    public Libro save(Libro libro) {
        AutoresEntity autorEntity = new AutoresEntity();
        autorEntity.setId(UUID.fromString(libro.Idautor()));
        LibroEntity libroEntity = new LibroEntity(libro.id(), libro.titulo(), autorEntity, libro.anio().getAnio());
        LibroEntity savedEntity = springDataLibroRepository.save(libroEntity);
        return new Libro(savedEntity.getId(), savedEntity.getTitulo(), savedEntity.getIdautor().getId().toString(), new AnioPublicacion(savedEntity.getAnioPublicacion()));

    }

}
