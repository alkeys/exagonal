package com.exagonal001.user.application.port.in.user;

public interface UpdateUserCase {
    void updateUser(String id, String nombre, String apellido);
}
