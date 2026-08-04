package com.exagonal001.user.application.port.in.user;

import com.exagonal001.user.domain.models.User;

public interface GetUserCase {
    User getUserById(String id);
    
}
