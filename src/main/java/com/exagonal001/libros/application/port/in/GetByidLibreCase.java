package com.exagonal001.libros.application.port.in;

import com.exagonal001.libros.controller.dto.LibroResponse;

public interface GetByidLibreCase {
    LibroResponse getById(String id);
}
