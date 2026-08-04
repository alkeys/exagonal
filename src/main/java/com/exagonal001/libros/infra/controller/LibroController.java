package com.exagonal001.libros.infra.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;

import com.exagonal001.libros.application.port.in.CreateLibroCase;
import com.exagonal001.libros.application.port.in.GetAllLibroCase;
import com.exagonal001.libros.application.port.in.GetByidLibreCase;
import com.exagonal001.libros.controller.dto.LibroRequest;
import com.exagonal001.libros.controller.dto.LibroResponse;
import com.exagonal001.libros.domain.models.AnioPublicacion;
import com.exagonal001.libros.domain.models.Libro;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    private final GetAllLibroCase getAllLibroCase;
    private final GetByidLibreCase getByidLibreCase;

    public LibroController(CreateLibroCase createLibroCase, GetAllLibroCase getAllLibroCase, GetByidLibreCase getByidLibreCase) {
        this.createLibroCase = createLibroCase;
        this.getAllLibroCase = getAllLibroCase;
        this.getByidLibreCase = getByidLibreCase;
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
        @PreAuthorize("hasRole('ADMIN')")
    @PostMapping()
    public LibroResponse createLibro(@RequestBody LibroRequest libro) {
        var createdLibro = createLibroCase.createLibro(libro);
        return createdLibro;
    }
    
    /**
     * Obtiene todos los libros registrados en el sistema.
     * @return una lista de libros
     */
    @Operation(summary = "Obtener todos los libros", description = "Retorna una lista con todos los libros registrados en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Libros obtenidos correctamente",
                    content = @Content(schema = @Schema(implementation = LibroResponse.class)))
    })
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping()
    public List<LibroResponse> getAllLibros() {
        return getAllLibroCase.getAllLibros();
    }

    /**
     * Obtiene un libro por su ID.
     * @param id el ID del libro a obtener
     * @return el libro encontrado
     */
    @Operation(summary = "Obtener un libro por ID", description = "Retorna un libro específico según su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Libro obtenido correctamente",
                    content = @Content(schema = @Schema(implementation = LibroResponse.class))),
            @ApiResponse(responseCode = "404", description = "Libro no encontrado", content = @Content())
    })
    @GetMapping("/{id}")
    public LibroResponse getById(@PathVariable String id) {
        return getByidLibreCase.getById(id);
    }

}