package com.exagonal001.user.infra.persistence;

import java.util.List;
import java.util.UUID;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;

import com.exagonal001.user.application.port.out.UserRepositoryPort;
import com.exagonal001.user.domain.models.User;
import com.exagonal001.user.infra.models.RolEntity;
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
    public User save(User user) {
        RolEntity rolEntity = new RolEntity(user.rolId()); // Solo se necesita el ID del rol
        UserEntity userEntity = new UserEntity(null, user.nombre(), user.apellido(), user.email(), rolEntity, user.password());
        UserEntity savedUser = springDataUserRepository.save(userEntity);
        return toDomain(savedUser);
    }

    /**
     * Obtiene todos los usuarios almacenados.
     *
     * @return lista de usuarios mapeados al dominio
     */
    @Override
    public List<User> getAllUsers() {
        return springDataUserRepository.findAll().stream()
                .map(this::toDomain)
                .toList();
    }

    /**
     * Obtiene un usuario por su identificador.
     *
     * @param id identificador del usuario
     * @return usuario encontrado o Optional.empty() si no se encuentra
     */
    @Override
    public User getUserById(String id) {
        return springDataUserRepository.findById(UUID.fromString(id))
                .map(this::toDomain)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public User findByEmail(String email) {
        return springDataUserRepository.findByEmail(email)
                .map(this::toDomain)
                .orElseThrow(() -> new RuntimeException("User not found"));
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


    /**
     * Elimina un usuario por su identificador.
     *
     * @param id identificador del usuario a eliminar
     */
    @Override
    public void deleteUser(String id) {
        UserEntity userEntity = springDataUserRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new RuntimeException("User not found"));
        try {
            springDataUserRepository.delete(userEntity);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("No se puede eliminar el usuario: tiene préstamos asociados", e);
        }
    }

    public boolean existsByRol(String rol) {
        return springDataUserRepository.existsByRolNombre(rol);
    }

    private User toDomain(UserEntity userEntity) {
        return new User(
                userEntity.getId(),
                userEntity.getNombre(),
                userEntity.getApellido(),
                userEntity.getEmail(),
                userEntity.getRol().getId(),
                userEntity.getPassword());
    }

}