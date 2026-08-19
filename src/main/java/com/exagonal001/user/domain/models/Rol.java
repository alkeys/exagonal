package com.exagonal001.user.domain.models;

import java.sql.Date;
import java.util.UUID;

import com.exagonal001.user.domain.models.values.DescripcionRol;
import com.exagonal001.user.domain.models.values.NombreRol;



public record Rol(
    UUID id,
    NombreRol nombre,
    DescripcionRol descripcion,
    Date fechaCreacion,
    Boolean activo
) {

    public Rol(NombreRol nombre, DescripcionRol descripcion, Date fechaCreacion, Boolean activo) {
        this(null, nombre, descripcion, fechaCreacion, activo);
    }

    public Rol(String nombre, String descripcion,boolean activo) {
        this(null, new NombreRol(nombre), new DescripcionRol(descripcion), new Date(System.currentTimeMillis()), activo);
    }

}
