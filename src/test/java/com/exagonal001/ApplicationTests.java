package com.exagonal001;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
class ApplicationTests {

	@Test
	void contextLoads() {
	}



	@Test
	void esNacionalidadValida() {
		String nacionalidad = "salvadoreño";

		ObjectMapper objectMapper = new ObjectMapper();

		try (InputStream inputStream = getClass()
				.getClassLoader()
				.getResourceAsStream("Data/nacionalidades.json")) {

			if (inputStream == null) {
				throw new RuntimeException("No se encontró nacionalidades.json");
			}

			JsonNode jsonNode = objectMapper.readTree(inputStream);

			boolean existe = false;

			for (JsonNode pais : jsonNode.get("paises")) {
				if (pais.get("nacionalidad")
						.asText()
						.equalsIgnoreCase(nacionalidad)) {

					existe = true;
					break;
				}
			}

			assertTrue(existe);

		} catch (IOException e) {
			throw new RuntimeException("Error al leer el archivo JSON", e);
		}
	}
    

}
