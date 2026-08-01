package com.exagonal001.autores.application.port.in;

import com.exagonal001.autores.domain.models.Autore;

public interface CreateAutoresCase {
    Autore createAutores(Autore autores);
    
}
