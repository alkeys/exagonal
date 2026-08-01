package com.exagonal001.autores.domain.models;

import java.util.UUID;

import com.exagonal001.autores.domain.models.values.Apellido;
import com.exagonal001.autores.domain.models.values.Fecha;
import com.exagonal001.autores.domain.models.values.Nacionalidad;
import com.exagonal001.autores.domain.models.values.Nombre;

public record Autore(
        UUID id,
        Nombre nombre,
        Apellido apellido,
        Nacionalidad nacionalidad,
        Fecha fechaNacimiento,
        Fecha fechaFallecimiento)
         {

}
