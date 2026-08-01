package com.exagonal001.autores.application.port.out;

import com.exagonal001.autores.domain.models.Autore;

public interface AutoresRepositoryPort {

    Autore save(Autore autores);
    
}
