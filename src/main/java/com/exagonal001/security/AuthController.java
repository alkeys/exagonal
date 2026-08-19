package com.exagonal001.security;

import org.springframework.security.access.prepost.PreAuthorize;


import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exagonal001.user.domain.models.User;
import com.exagonal001.user.infra.adapter.in.web.dto.User.UserRequest;
import com.exagonal001.user.infra.adapter.in.web.dto.User.UserResponse;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthService.AuthResponse> login(@RequestBody AuthService.LoginRequest request) {
        AuthService.AuthResult result = authService.login(request);
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, result.cookie().toString())
                .body(result.response());
    }

    @PostMapping("/register")
    public UserResponse register(@RequestBody UserRequest request) {
        //solo registro de usuario 
        User created = authService.registerUser(toDomain(request));
        return toResponse(created);
    }

    @PostMapping("/admin/register")
    @PreAuthorize("hasRole('ADMIN')")
    public UserResponse registerAdmin(@RequestBody UserRequest request) {
        String role = request.rol() == null || request.rol().isBlank() ? "USER" : request.rol();
        User created = authService.registerWithRole(toDomain(request), role);
        return toResponse(created);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout() {
        return ResponseEntity.noContent()
                .header(HttpHeaders.SET_COOKIE, authService.logoutCookie().toString())
                .build();
    }

    private User toDomain(UserRequest request) {
        return new User(null, request.nombre(), request.apellido(), request.email(), null, request.password());
    }

    private UserResponse toResponse(User user) {
        return new UserResponse(user.id(), user.nombre(), user.apellido(), user.email(), user.rolId().toString());
    }
}