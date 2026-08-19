package com.exagonal001.libros.infra.out.persistence.adapter;

import java.util.List;
import java.util.UUID;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;

import com.exagonal001.autores.infra.adapter.out.persistence.entity.AutoresEntity;
import com.exagonal001.libros.application.port.out.LibroRepositoryPort;
import com.exagonal001.libros.domain.models.Libro;
import com.exagonal001.libros.domain.models.values.AnioPublicacion;
import com.exagonal001.libros.domain.models.values.Cantidad;
import com.exagonal001.libros.infra.out.persistence.entity.LibroEntity;
import com.exagonal001.libros.infra.out.persistence.repository.SpringDataLibroRepository;

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
        libroEntity.setCantidadDisponible(libro.cantidadDisponible().getCantidad());
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

    @Override
    public Libro update(String id, Libro libro) {
        LibroEntity libroEntity = springDataLibroRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new RuntimeException("Libro no encontrado con id: " + id));

        AutoresEntity autorEntity = new AutoresEntity();
        autorEntity.setId(UUID.fromString(libro.Idautor()));

        libroEntity.setTitulo(libro.titulo());
        libroEntity.setIdautor(autorEntity);
        libroEntity.setAnioPublicacion(libro.anio().getAnio());
        libroEntity.setUrl(libro.url());
        libroEntity.setCantidadDisponible(libro.cantidadDisponible().getCantidad());

        LibroEntity updatedLibro = springDataLibroRepository.save(libroEntity);
        return toDomain(updatedLibro);
    }

    @Override
    public void delete(String id) {
        LibroEntity libroEntity = springDataLibroRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new RuntimeException("Libro no encontrado con id: " + id));
        try {
            springDataLibroRepository.delete(libroEntity);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("No se puede eliminar el libro: tiene prestamos asociados", e);
        }
    }

    private Libro toDomain(LibroEntity libroEntity) {
        return new Libro(
                libroEntity.getId(),
                libroEntity.getTitulo(),
                libroEntity.getIdautor().getId().toString(),
                new AnioPublicacion(libroEntity.getAnioPublicacion()),
                libroEntity.getUrl(),
                new Cantidad(libroEntity.getCantidadDisponible())
        );
    }

}
