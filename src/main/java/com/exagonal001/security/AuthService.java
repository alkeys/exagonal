package com.exagonal001.security;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.exagonal001.user.controller.dto.UserRequest;
import com.exagonal001.user.controller.dto.UserResponse;
import com.exagonal001.user.infra.models.UserEntity;
import com.exagonal001.user.infra.persistence.SpringDataUserRepository;

@Service
public class AuthService {

    private final SpringDataUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(SpringDataUserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public AuthResponse login(LoginRequest request) {
        UserEntity user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new IllegalArgumentException("Usuario o contraseña invalidos"));

        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new IllegalArgumentException("Usuario o contraseña invalidos");
        }

        String token = jwtService.generateToken(user.getId(), user.getEmail(), user.getRol());
        return new AuthResponse(token, jwtService.getExpirationMs(), user.getRol(), new UserResponse(
                user.getId(), user.getNombre(), user.getApellido(), user.getEmail(), user.getRol()));
    }

    public UserResponse registerUser(UserRequest request) {
        return registerWithRole(request, "USER");
    }

    public UserResponse registerWithRole(UserRequest request, String role) {
        String encodedPassword = passwordEncoder.encode(request.password());
        UserEntity savedUser = userRepository.save(new UserEntity(
                null,
                request.nombre(),
                request.apellido(),
                request.email(),
                role,
                encodedPassword));

        return new UserResponse(savedUser.getId(), savedUser.getNombre(), savedUser.getApellido(), savedUser.getEmail(), savedUser.getRol());
    }

    public record LoginRequest(String email, String password) {
    }

    public record AuthResponse(String token, long expiresInMs, String role, UserResponse user) {
    }
}