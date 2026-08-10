package com.exagonal001.libros.infra.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exagonal001.libros.application.port.in.CreatePrestamoCase;
import com.exagonal001.libros.application.port.in.DeletePrestamoCase;
import com.exagonal001.libros.application.port.in.GetAllPrestamoCase;
import com.exagonal001.libros.application.port.in.GetPrestamoCase;
import com.exagonal001.libros.application.port.in.UpdatePrestamoCase;
import com.exagonal001.libros.controller.dto.PrestamoRequest;
import com.exagonal001.libros.controller.dto.PrestamoResponse;
import com.exagonal001.libros.controller.dto.PrestamoUpdateRequest;
import com.exagonal001.libros.domain.models.PrestamosLibros;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.security.access.prepost.PreAuthorize;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;



@RestController
@RequestMapping("/prestamos")
@Tag(name = "Prestamos", description = "Operaciones para la gestion de prestamos de libros")
public class PrestamoController {

    private final CreatePrestamoCase createPrestamoCase;
    private final GetAllPrestamoCase getAllPrestamoCase;
    private final GetPrestamoCase getPrestamoCase;
    private final UpdatePrestamoCase updatePrestamoCase;
    private final DeletePrestamoCase deletePrestamoCase;

    public PrestamoController(CreatePrestamoCase createPrestamoCase, GetAllPrestamoCase getAllPrestamoCase, GetPrestamoCase getPrestamoCase, UpdatePrestamoCase updatePrestamoCase, DeletePrestamoCase deletePrestamoCase) {
        this.createPrestamoCase = createPrestamoCase;
        this.getAllPrestamoCase = getAllPrestamoCase;
        this.getPrestamoCase = getPrestamoCase;
        this.updatePrestamoCase = updatePrestamoCase;
        this.deletePrestamoCase = deletePrestamoCase;
    }

    /**
     * Crea un nuevo prestamo.
     * @param prestamo los datos del prestamo a crear
     * @return el prestamo creado
     */
    @Operation(summary = "Crear un prestamo", description = "Registra el prestamo de un libro a un usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Prestamo creado correctamente",
                    content = @Content(schema = @Schema(implementation = PrestamoResponse.class))),
            @ApiResponse(responseCode = "400", description = "Datos de entrada invalidos", content = @Content())
    })
        @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @PostMapping("")
    public PrestamoResponse createPrestamo(@RequestBody PrestamoRequest prestamo) {
        var prestamoDomain = new PrestamosLibros(
            null,
            UUID.fromString(prestamo.idUsuario()),
            UUID.fromString(prestamo.idLibro()),
            java.time.LocalDate.parse(prestamo.fechaPrestamo()),
            java.time.LocalDate.parse(prestamo.fechaDevolucion())
        );
        var createdPrestamo = createPrestamoCase.createPrestamo(prestamoDomain);
        return toResponse(createdPrestamo);
    }

    /**
     * Obtiene todos los prestamos registrados.
     * @return lista de prestamos
     */
    @Operation(summary = "Obtener todos los prestamos", description = "Retorna una lista con todos los prestamos registrados en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Prestamos obtenidos correctamente",
                    content = @Content(schema = @Schema(implementation = PrestamoResponse.class)))
    })
        @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("")
    public List<PrestamoResponse> getAllPrestamos() {
        return getAllPrestamoCase.getAllPrestamos().stream()
                .map(this::toResponse)
                .toList();
    }

    /**
     * Obtiene un prestamo por su ID.
     * @param id el ID del prestamo a obtener
     * @return el prestamo encontrado
     */
    @Operation(summary = "Obtener un prestamo por ID", description = "Retorna un prestamo específico según su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Prestamo obtenido correctamente",
                    content = @Content(schema = @Schema(implementation = PrestamoResponse.class))),
            @ApiResponse(responseCode = "404", description = "Prestamo no encontrado", content = @Content())
    })
        @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/{id}")
    public PrestamoResponse getPrestamoById(@PathVariable String id) {
        return toResponse(getPrestamoCase.getPrestamoById(id));
    }

    /**
     * Actualiza las fechas de un prestamo existente.
     * @param id el ID del prestamo a actualizar
     * @param prestamo las nuevas fechas del prestamo
     * @return el prestamo actualizado
     */
    @Operation(summary = "Actualizar un prestamo", description = "Actualiza las fechas de un prestamo existente según su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Prestamo actualizado correctamente",
                    content = @Content(schema = @Schema(implementation = PrestamoResponse.class))),
            @ApiResponse(responseCode = "404", description = "Prestamo no encontrado", content = @Content())
    })
        @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @PutMapping("/{id}")
    public PrestamoResponse updatePrestamo(@PathVariable String id, @RequestBody PrestamoUpdateRequest prestamo) {
        var updatedPrestamo = updatePrestamoCase.updatePrestamo(
                id,
                LocalDate.parse(prestamo.fechaPrestamo()),
                LocalDate.parse(prestamo.fechaDevolucion()));
        return toResponse(updatedPrestamo);
    }

    /**
     * Elimina un prestamo por su ID.
     * @param id el ID del prestamo a eliminar
     */
    @Operation(summary = "Eliminar un prestamo", description = "Elimina un prestamo del sistema según su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Prestamo eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Prestamo no encontrado", content = @Content())
    })
        @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @DeleteMapping("/{id}")
    public void deletePrestamo(@PathVariable String id) {
        deletePrestamoCase.deletePrestamo(id);
    }

    private PrestamoResponse toResponse(PrestamosLibros prestamo) {
        return new PrestamoResponse(
            prestamo.idPrestamo().toString(),
            prestamo.idUsuario().toString(),
            prestamo.idLibro().toString(),
            prestamo.fechaPrestamo().toString(),
            prestamo.fechaDevolucion().toString()
        );
    }

}
