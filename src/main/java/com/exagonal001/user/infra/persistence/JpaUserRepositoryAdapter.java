package com.exagonal001.user.infra.persistence;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.exagonal001.user.application.port.out.UserRepositoryPort;
import com.exagonal001.user.controller.dto.UserResponse;
import com.exagonal001.user.domain.models.User;
import com.exagonal001.user.infra.models.UserEntity;

/**
 * Adaptador JPA que implementa el puerto de persistencia de usuarios.
 */
@Repository
public class JpaUserRepositoryAdapter implements UserRepositoryPort {

    private final SpringDataUserRepository springDataUserRepository;

    /**
     * Crea el adaptador con el repositorio de Spring Data.
     *
     * @param springDataUserRepository repositorio de persistencia JPA
     */
    public JpaUserRepositoryAdapter(SpringDataUserRepository springDataUserRepository) {
        this.springDataUserRepository = springDataUserRepository;
    }

    /**
     * Persiste un usuario y devuelve el registro guardado.
     *
     * @param user usuario a persistir
     * @return usuario persistido con su identificador generado
     */
    @Override
    public Optional<User> save(User user) {
        UserEntity userEntity = new UserEntity(user.id(), user.nombre(), user.apellido(), user.rol(), user.password());
        UserEntity savedUser = springDataUserRepository.save(userEntity);
        return Optional.of(new User(savedUser.getId(), savedUser.getNombre(), savedUser.getApellido(), savedUser.getRol(), savedUser.getPassword()));
    }

    /**
     * Obtiene todos los usuarios almacenados.
     *
     * @return lista de usuarios mapeados al dominio
     */
    @Override
    public List<UserResponse> getAllUsers() {
        return springDataUserRepository.findAll().stream()
                .map(userEntity -> new UserResponse(userEntity.getId(), userEntity.getNombre(), userEntity.getApellido(), userEntity.getRol()))
                .toList();
    }

    /**
     * Obtiene un usuario por su identificador.
     *
     * @param id identificador del usuario
     * @return usuario encontrado o Optional.empty() si no se encuentra
     */
    @Override
    public UserResponse getUserById(String id) {
        return springDataUserRepository.findById(UUID.fromString(id))
                .map(userEntity -> new UserResponse(userEntity.getId(), userEntity.getNombre(), userEntity.getApellido(), userEntity.getRol()))
                .orElse(null);
    }

    /**
     * Actualiza un usuario por su identificador.
     *
     * @param id identificador del usuario
     * @param nombre nombre del usuario
     * @param apellido apellido del usuario
     */
    @Override
    public void updateUser(String id, String nombre, String apellido) {
        UserEntity userEntity = springDataUserRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new RuntimeException("User not found"));
        userEntity.setNombre(nombre);
        userEntity.setApellido(apellido);
        springDataUserRepository.save(userEntity);
    }

}