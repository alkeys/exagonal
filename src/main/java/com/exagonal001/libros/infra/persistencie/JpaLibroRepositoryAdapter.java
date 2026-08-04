package com.exagonal001.libros.infra.persistencie;

import java.util.List;
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
        LibroEntity libroEntity = new LibroEntity();
        libroEntity.setTitulo(libro.titulo());
        libroEntity.setAnioPublicacion(libro.anio().getAnio());
        libroEntity.setUrl(libro.url());
        libroEntity.setIdautor(autorEntity);
        LibroEntity savedLibro = springDataLibroRepository.save(libroEntity);
        return toDomain(savedLibro);
    }


    @Override
    public List<Libro> findAll() {
        List<LibroEntity> libros = springDataLibroRepository.findAll();
        return libros.stream()
                .map(this::toDomain)
                .toList();
    } 

    @Override
    public Libro findById(String id) {
        LibroEntity libro = springDataLibroRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new RuntimeException("Libro no encontrado con id: " + id));
        return toDomain(libro);
    }

    private Libro toDomain(LibroEntity libroEntity) {
        return new Libro(
                libroEntity.getId(),
                libroEntity.getTitulo(),
                libroEntity.getIdautor().getId().toString(),
                new AnioPublicacion(libroEntity.getAnioPublicacion()),
                libroEntity.getUrl()
        );
    }

}
