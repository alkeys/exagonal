package com.exagonal001.libros.application.port.in;

import java.util.List;

import com.exagonal001.libros.controller.dto.LibroResponse;

public interface GetAllLibroCase {
    List<LibroResponse> getAllLibros();
    
}
