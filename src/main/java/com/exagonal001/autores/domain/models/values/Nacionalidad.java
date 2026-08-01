package com.exagonal001.autores.domain.models.values;

import java.io.FileReader;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import lombok.Value;

@Value
public class Nacionalidad {
    private final String nacionalidad;
    public Nacionalidad(String nacionalidad) {
        if(nacionalidad == null || nacionalidad.isBlank() || !esNacionalidadValida(nacionalidad)) {
            throw new IllegalArgumentException("La nacionalidad no puede ser nula o vacía y debe ser válida");
        }
   

        this.nacionalidad = nacionalidad.toUpperCase();
    }



    private Boolean esNacionalidadValida(String nacionalidad) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            File file = new File("src/main/resources/Data/nacionalidades.json");
            JsonNode jsonNode = objectMapper.readTree(file);

            for (JsonNode pais : jsonNode.get("paises")) {
                if (pais.get("nacionalidad").asText().equalsIgnoreCase(nacionalidad)) {
                    return true;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error al leer el archivo JSON", e);
        }

        return false;
    }


}
