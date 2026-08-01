package com.exagonal001.autores.application.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.exagonal001.autores.application.port.in.CreateAutoresCase;
import com.exagonal001.autores.application.port.in.GetallAutoresCase;
import com.exagonal001.autores.application.port.out.AutoresRepositoryPort;
import com.exagonal001.autores.domain.models.Autore;

@Service
public class AutoresService implements CreateAutoresCase, GetallAutoresCase {

    private final AutoresRepositoryPort autoresRepositoryPort;

    public AutoresService(AutoresRepositoryPort autoresRepositoryPort) {
        this.autoresRepositoryPort = autoresRepositoryPort;
    }

    @Override
    public Autore createAutores(Autore autores) {
        return autoresRepositoryPort.save(autores);
    }

    @Override
    public List<Autore> findAll() {
        return autoresRepositoryPort.findAll();
    }

}
