package com.exagonal001.libros.infra.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exagonal001.libros.application.port.in.CreatePrestamoCase;
import com.exagonal001.libros.controller.dto.PrestamoRequest;
import com.exagonal001.libros.controller.dto.PrestamoResponse;
import com.exagonal001.libros.domain.models.PrestamosLibros;

import java.util.UUID;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
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

    public PrestamoController(CreatePrestamoCase createPrestamoCase) {
        this.createPrestamoCase = createPrestamoCase;
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
        return new PrestamoResponse(
            createdPrestamo.idPrestamo().toString(),
            createdPrestamo.idUsuario().toString(),
            createdPrestamo.idLibro().toString(),
            createdPrestamo.fechaPrestamo().toString(),
            createdPrestamo.fechaDevolucion().toString()
        );
    }
    

}
