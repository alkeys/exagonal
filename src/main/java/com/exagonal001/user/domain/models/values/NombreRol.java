package com.exagonal001.user.domain.models.values;

import lombok.Value;

@Value
public class NombreRol {
   String nombre;
   public NombreRol(String nombre) {
      if (nombre == null || nombre.isBlank()) {
         throw new IllegalArgumentException("El nombre del rol no puede ser nulo o vacío");
      }
      this.nombre = nombre;
   }
    
}
