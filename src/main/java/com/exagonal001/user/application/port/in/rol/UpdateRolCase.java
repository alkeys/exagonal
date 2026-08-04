package com.exagonal001.user.application.port.in.rol;

import com.exagonal001.user.domain.models.Rol;

public interface UpdateRolCase {
    void updateRol(String id,Rol rol);
}
