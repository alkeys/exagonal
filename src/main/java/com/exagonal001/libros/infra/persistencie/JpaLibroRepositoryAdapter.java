package com.exagonal001.libros.infra.persistencie;

import org.springframework.stereotype.Repository;

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
        LibroEntity libroEntity = new LibroEntity(libro.id(), libro.titulo(), libro.autor(), libro.anio().getAnio());
        LibroEntity savedEntity = springDataLibroRepository.save(libroEntity);
        return new Libro(savedEntity.getId(), savedEntity.getTitulo(), savedEntity.getAutor(), new AnioPublicacion(savedEntity.getAnioPublicacion()));

    }

}
