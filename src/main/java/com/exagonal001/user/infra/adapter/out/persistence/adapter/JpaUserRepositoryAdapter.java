package com.exagonal001.user.infra.adapter.out.persistence.adapter;

import java.util.List;
import java.util.UUID;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;

import com.exagonal001.user.application.port.out.UserRepositoryPort;
import com.exagonal001.user.domain.models.User;
import com.exagonal001.user.infra.adapter.out.persistence.repository.SpringDataUserRepository;
import com.exagonal001.user.infra.adapter.out.persistence.entity.RolEntity;
import com.exagonal001.user.infra.adapter.out.persistence.entity.UserEntity;

@Repository
public class JpaUserRepositoryAdapter implements UserRepositoryPort {

    private final SpringDataUserRepository springDataUserRepository;

    public JpaUserRepositoryAdapter(SpringDataUserRepository springDataUserRepository) {
        this.springDataUserRepository = springDataUserRepository;
    }

    @Override
    public User save(User user) {
        RolEntity rolEntity = new RolEntity(user.rolId());
        UserEntity userEntity = new UserEntity(null, user.nombre(), user.apellido(), user.email(), rolEntity, user.password());
        UserEntity savedUser = springDataUserRepository.save(userEntity);
        return toDomain(savedUser);
    }

    @Override
    public List<User> getAllUsers() {
        return springDataUserRepository.findAll().stream()
                .map(this::toDomain)
                .toList();
    }

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

    @Override
    public void updateUser(String id, String nombre, String apellido) {
        UserEntity userEntity = springDataUserRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new RuntimeException("User not found"));
        userEntity.setNombre(nombre);
        userEntity.setApellido(apellido);
        springDataUserRepository.save(userEntity);
    }

    @Override
    public void deleteUser(String id) {
        UserEntity userEntity = springDataUserRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new RuntimeException("User not found"));
        try {
            springDataUserRepository.delete(userEntity);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("No se puede eliminar el usuario: tiene prestamos asociados", e);
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
