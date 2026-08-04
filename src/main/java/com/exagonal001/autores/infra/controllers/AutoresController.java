package com.exagonal001.autores.infra.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.access.prepost.PreAuthorize;

import com.exagonal001.autores.application.port.in.CreateAutoresCase;
import com.exagonal001.autores.application.port.in.GetallAutoresCase;
import com.exagonal001.autores.controller.dto.AutoresRequest;
import com.exagonal001.autores.controller.dto.AutoresResponse;
import com.exagonal001.autores.domain.models.Autore;
import com.exagonal001.autores.domain.models.values.Apellido;
import com.exagonal001.autores.domain.models.values.Fecha;
import com.exagonal001.autores.domain.models.values.Nacionalidad;
import com.exagonal001.autores.domain.models.values.Nombre;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@RequestMapping("/autores")
@Tag(name = "Autores", description = "Operaciones para la gestion de autores")
public class AutoresController {

    private final CreateAutoresCase createAutorCase;
    private final GetallAutoresCase getAllAutoresCase;

    public AutoresController(CreateAutoresCase createAutorCase, GetallAutoresCase getAllAutoresCase) {
        this.createAutorCase = createAutorCase;
        this.getAllAutoresCase = getAllAutoresCase;
    }

    /**
     * Crea un nuevo autor.
     * @param autor El autor a crear.
     * @return El autor creado.
     */
    @Operation(summary = "Crear un autor", description = "Registra un nuevo autor en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Autor creado correctamente",
                    content = @Content(schema = @Schema(implementation = AutoresResponse.class))),
            @ApiResponse(responseCode = "400", description = "Datos de entrada invalidos", content = @Content())
    })
    
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public AutoresResponse createAutor(@RequestBody AutoresRequest autor) {
        var autorDomain = new Autore(
            null,
            new Nombre(autor.nombre()),
            new Apellido(autor.apellido()),
            new Nacionalidad(autor.nacionalidad()),
            new Fecha (autor.anioNacimiento()),
            new Fecha(autor.anioFallecimiento())
        );
        var createdAutor = createAutorCase.createAutores(autorDomain);
        return new AutoresResponse(
            createdAutor.id().toString(),
            createdAutor.nombre().getNombre(),
            createdAutor.apellido().getApellido(),
            createdAutor.nacionalidad().getNacionalidad(),
            createdAutor.fechaNacimiento().getFecha(),
            createdAutor.fechaFallecimiento().getFecha()
        );
    }

    /**
     * Obtiene todos los autores.
     * @return La lista de autores.
     */
    @Operation(summary = "Listar autores", description = "Obtiene la lista de todos los autores registrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de autores obtenida correctamente",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = AutoresResponse.class))))
    })
        @PreAuthorize("isAuthenticated()")
    @GetMapping()
    public List<AutoresResponse> getAllAutores() {
        var autores = getAllAutoresCase.findAll();
        return autores.stream()
                .map(autor -> new AutoresResponse(
                        autor.id().toString(),
                        autor.nombre().getNombre(),
                        autor.apellido().getApellido(),
                        autor.nacionalidad().getNacionalidad(),
                        autor.fechaNacimiento().getFecha(),
                        autor.fechaFallecimiento().getFecha()
                ))
                .toList();
    }
    
    
}
