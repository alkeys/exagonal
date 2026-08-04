package com.exagonal001.security;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import org.springframework.http.ResponseCookie;

import com.exagonal001.user.application.port.out.UserRepositoryPort;
import com.exagonal001.user.domain.models.User;

@Service
public class AuthService {

    private final UserRepositoryPort userRepositoryPort;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(UserRepositoryPort userRepositoryPort, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepositoryPort = userRepositoryPort;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public AuthResult login(LoginRequest request) {
        User user = userRepositoryPort.findByEmail(request.email());

        if (!passwordEncoder.matches(request.password(), user.password())) {
            throw new IllegalArgumentException("Usuario o contraseña invalidos");
        }

        String token = jwtService.generateToken(user.id(), user.email(), user.rol());
        AuthResponse response = new AuthResponse(
                token,
                jwtService.getExpirationMs(),
                user.rol(),
                new UserPayload(
                        user.id() != null ? user.id().toString() : null,
                        user.nombre(),
                        user.apellido(),
                        user.email(),
                        user.rol()));
        ResponseCookie cookie = jwtService.createAuthCookie(token);
        return new AuthResult(response, cookie);
    }

    public User registerUser(User request) {
        return registerWithRole(request, "USER");
    }

    public User registerWithRole(User request, String role) {
        String encodedPassword = passwordEncoder.encode(request.password());
        User userToSave = new User(
                null,
                request.nombre(),
                request.apellido(),
                request.email(),
                role,
                encodedPassword);

        return userRepositoryPort.save(userToSave);
    }

    public record LoginRequest(String email, String password) {
    }

    public record UserPayload(String id, String nombre, String apellido, String email, String rol) {
    }

    public record AuthResponse(String token, long expiresInMs, String role, UserPayload user) {
    }

    public record AuthResult(AuthResponse response, ResponseCookie cookie) {
    }

    public ResponseCookie logoutCookie() {
        return jwtService.createLogoutCookie();
    }
}