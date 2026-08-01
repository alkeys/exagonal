package com.exagonal001.autores.application.service;

import org.springframework.stereotype.Service;

import com.exagonal001.autores.application.port.in.CreateAutoresCase;
import com.exagonal001.autores.application.port.out.AutoresRepositoryPort;
import com.exagonal001.autores.domain.models.Autore;

@Service
public class AutoresService implements CreateAutoresCase {

    private final AutoresRepositoryPort autoresRepositoryPort;

    public AutoresService(AutoresRepositoryPort autoresRepositoryPort) {
        this.autoresRepositoryPort = autoresRepositoryPort;
    }

    @Override
    public Autore createAutores(Autore autores) {
        return autoresRepositoryPort.save(autores);
    }
    
}
