package com.exagonal001.autores.application.port.out;

import java.util.List;

import com.exagonal001.autores.controller.dto.AutoresResponse;
import com.exagonal001.autores.domain.models.Autore;

public interface AutoresRepositoryPort {

    Autore save(Autore autores);
    List<Autore> findAll();
    AutoresResponse findById(String id);
    
}
