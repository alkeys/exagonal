package com.exagonal001.autores.application.port.in;

import java.util.List;

import com.exagonal001.autores.domain.models.Autore;

public interface GetallAutoresCase {
    List<Autore> findAll();
}
