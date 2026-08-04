package com.exagonal001.libros.infra.persistencie;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.exagonal001.autores.infra.models.AutoresEntity;
import com.exagonal001.autores.infra.persistencie.SpringDataAutoresRepository;
import com.exagonal001.libros.application.port.out.LibroRepositoryPort;
import com.exagonal001.libros.controller.dto.LibroRequest;
import com.exagonal001.libros.controller.dto.LibroResponse;
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
    public LibroResponse save(LibroRequest libro) {
        // Crear la entidad de autor
        AutoresEntity autorEntity = new AutoresEntity();
        autorEntity.setId(UUID.fromString(libro.autor()));
        LibroEntity libroEntity = new LibroEntity();
        libroEntity.setTitulo(libro.titulo());
        libroEntity.setAnioPublicacion(libro.anio());
        libroEntity.setUrl(libro.url());
        libroEntity.setIdautor(autorEntity);
        LibroEntity savedLibro = springDataLibroRepository.save(libroEntity);
        return new LibroResponse(
            savedLibro.getId().toString(),
            savedLibro.getTitulo(),
            savedLibro.getIdautor().getId().toString(),
            savedLibro.getAnioPublicacion(),
            savedLibro.getUrl()
        );
        
    }


    @Override
    public List<LibroResponse> findAll() {
        List<LibroEntity> libros = springDataLibroRepository.findAll();
        return libros.stream()
                .map(libro -> new LibroResponse(
                        libro.getId().toString(),
                        libro.getTitulo(),
                        libro.getIdautor().getId().toString(),
                        libro.getAnioPublicacion(), 
                        libro.getUrl()
                ))
                .toList();
    } 

    @Override
    public LibroResponse findById(String id) {
        LibroEntity libro = springDataLibroRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new RuntimeException("Libro no encontrado con id: " + id));
        return new LibroResponse(
                libro.getId().toString(),
                libro.getTitulo(),
                libro.getIdautor().getId().toString(),
                libro.getAnioPublicacion(),
                libro.getUrl()
        );
    }

}
