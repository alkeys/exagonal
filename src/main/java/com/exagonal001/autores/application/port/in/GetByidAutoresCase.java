package com.exagonal001.autores.application.port.in;

import com.exagonal001.autores.controller.dto.AutoresResponse;

public interface GetByidAutoresCase {
    AutoresResponse getById(String id);
}
