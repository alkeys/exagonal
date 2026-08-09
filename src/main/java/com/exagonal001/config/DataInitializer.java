package com.exagonal001.config;




import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import com.exagonal001.user.application.service.RolService;
import com.exagonal001.user.application.service.UserService;
import com.exagonal001.user.domain.models.Rol;
import com.exagonal001.user.domain.models.User;

import jakarta.transaction.Transactional;

@Configuration
public class DataInitializer {


    @Bean
    CommandLineRunner initDatabase(UserService userService,RolService rolService, InitializerHelper helper) {
        return args -> {
            helper.initialize(userService, rolService);
        };
    }
}

@Component
class InitializerHelper {

    @Transactional
    public void initialize(UserService userService, RolService rolService) {
        Rol adminRol[] = new Rol[2];
        adminRol[0]=new Rol("ADMIN", "Administrador", true);
        adminRol[1]=new Rol("USER", "USER", true);
        if (!rolService.existsByName(adminRol[0].nombre().getNombre())) {
            adminRol[0] = rolService.createRol(adminRol[0]);
            rolService.createRol(adminRol[1]);
        } else {
            adminRol[0] = rolService.getAllRoles().stream()
                    .filter(rol -> "ADMIN".equals(rol.nombre().getNombre()))
                    .findFirst()
                    .orElseThrow(() -> new IllegalStateException("No se encontro el rol ADMIN"));
        }
        if (!userService.existsByRol("ADMIN")) {
            userService.createUser(new User(
                    null,
                    "Alex",
                    "Moran",
                    "admin@admin.com",
                    adminRol[0].id(),
                    "admin"
            ));
        }
    }
        
}

