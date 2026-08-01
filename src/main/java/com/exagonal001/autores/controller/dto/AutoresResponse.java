package com.exagonal001.autores.controller.dto;

import java.sql.Date;

public record AutoresResponse(
    String id,
    String nombre,
    String apellido,
    String nacionalidad,
    int anioNacimiento,
    Integer anioFallecimiento
) {
    
}
