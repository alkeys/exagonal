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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@RequestMapping("/libros")
@Tag(name = "Libros", description = "Operaciones para la gestion de libros")
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
    @Operation(summary = "Crear un libro", description = "Registra un nuevo libro en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Libro creado correctamente",
                    content = @Content(schema = @Schema(implementation = LibroResponse.class))),
            @ApiResponse(responseCode = "400", description = "Datos de entrada invalidos", content = @Content())
    })
    @PostMapping()
    public LibroResponse createLibro(@RequestBody LibroRequest libro) {
        var libroDomain = new Libro(
            null,
            libro.titulo(),
            libro.autor(),
            new AnioPublicacion(libro.anio()),
            libro.url()
        );
        var createdLibro = createLibroCase.createLibro(libroDomain);
        return new LibroResponse(
            createdLibro.id().toString(),
            createdLibro.titulo(),
            createdLibro.Idautor(),
            createdLibro.anio().getAnio(),
            createdLibro.url()

        );
    }
    
    
    
}
