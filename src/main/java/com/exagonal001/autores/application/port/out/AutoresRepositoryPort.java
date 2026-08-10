package com.exagonal001.autores.application.port.out;

import java.util.List;

import com.exagonal001.autores.domain.models.Autore;

public interface AutoresRepositoryPort {

    Autore save(Autore autores);
    List<Autore> findAll();
    Autore findById(String id);
    Autore update(String id, Autore autores);
    void delete(String id);
    
}
