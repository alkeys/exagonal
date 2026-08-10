package com.exagonal001.libros.infra.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;

import com.exagonal001.libros.application.port.in.CreateLibroCase;
import com.exagonal001.libros.application.port.in.DeleteLibroCase;
import com.exagonal001.libros.application.port.in.GetAllLibroCase;
import com.exagonal001.libros.application.port.in.GetByidLibreCase;
import com.exagonal001.libros.application.port.in.UpdateLibroCase;
import com.exagonal001.libros.controller.dto.LibroRequest;
import com.exagonal001.libros.controller.dto.LibroResponse;
import com.exagonal001.libros.domain.models.AnioPublicacion;
import com.exagonal001.libros.domain.models.Libro;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
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
    private final UpdateLibroCase updateLibroCase;
    private final DeleteLibroCase deleteLibroCase;

    public LibroController(CreateLibroCase createLibroCase, GetAllLibroCase getAllLibroCase, GetByidLibreCase getByidLibreCase, UpdateLibroCase updateLibroCase, DeleteLibroCase deleteLibroCase) {
        this.createLibroCase = createLibroCase;
        this.getAllLibroCase = getAllLibroCase;
        this.getByidLibreCase = getByidLibreCase;
        this.updateLibroCase = updateLibroCase;
        this.deleteLibroCase = deleteLibroCase;
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
            var createdLibro = createLibroCase.createLibro(toDomain(libro));
            return toResponse(createdLibro);
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
    @GetMapping()
    public List<LibroResponse> getAllLibros() {
        return getAllLibroCase.getAllLibros().stream()
                .map(this::toResponse)
                .toList();
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
        return toResponse(getByidLibreCase.getById(id));
    }

    /**
     * Actualiza un libro existente por su ID.
     * @param id el ID del libro a actualizar
     * @param libro los nuevos datos del libro
     * @return el libro actualizado
     */
    @Operation(summary = "Actualizar un libro", description = "Actualiza los datos de un libro existente según su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Libro actualizado correctamente",
                    content = @Content(schema = @Schema(implementation = LibroResponse.class))),
            @ApiResponse(responseCode = "404", description = "Libro no encontrado", content = @Content())
    })
        @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public LibroResponse updateLibro(@PathVariable String id, @RequestBody LibroRequest libro) {
        var updatedLibro = updateLibroCase.updateLibro(id, toDomain(libro));
        return toResponse(updatedLibro);
    }

    /**
     * Elimina un libro por su ID.
     * @param id el ID del libro a eliminar
     */
    @Operation(summary = "Eliminar un libro", description = "Elimina un libro del sistema según su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Libro eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Libro no encontrado", content = @Content())
    })
        @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public void deleteLibro(@PathVariable String id) {
        deleteLibroCase.deleteLibro(id);
    }

    private Libro toDomain(LibroRequest libro) {
        return new Libro(
                null,
                libro.titulo(),
                libro.autor(),
                new AnioPublicacion(libro.anio()),
                libro.url());
    }

    private LibroResponse toResponse(Libro libro) {
        return new LibroResponse(
                libro.id() != null ? libro.id().toString() : null,
                libro.titulo(),
                libro.Idautor(),
                libro.anio().getAnio(),
                libro.url());
    }

}