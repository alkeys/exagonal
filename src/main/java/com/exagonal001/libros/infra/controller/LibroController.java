package com.exagonal001.libros.infra.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exagonal001.libros.application.port.in.CreateLibroCase;
import com.exagonal001.libros.controller.dto.LibroRequest;
import com.exagonal001.libros.controller.dto.LibroResponse;
import com.exagonal001.libros.domain.models.AnioPublicacion;
import com.exagonal001.libros.domain.models.Libro;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/libros")
public class LibroController {

    private final CreateLibroCase createLibroCase;

    public LibroController(CreateLibroCase createLibroCase) {
        this.createLibroCase = createLibroCase;
    }

    /**
     * Crea un nuevo libro.
     * @param libro el libro a crear
     * @return el libro creado
     */
    @PostMapping()
    public LibroResponse createLibro(@RequestBody LibroRequest libro) {
        var libroDomain = new Libro(
            null,
            libro.titulo(),
            libro.autor(),
            new AnioPublicacion(libro.anio())
        );
        var createdLibro = createLibroCase.createLibro(libroDomain);
        return new LibroResponse(
            createdLibro.id().toString(),
            createdLibro.titulo(),
            createdLibro.Idautor(),
            createdLibro.anio().getAnio()

        );
    }
    
    
    
}
