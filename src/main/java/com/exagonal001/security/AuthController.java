package com.exagonal001.security;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exagonal001.user.controller.dto.UserRequest;
import com.exagonal001.user.controller.dto.UserResponse;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public AuthService.AuthResponse login(@RequestBody AuthService.LoginRequest request) {
        return authService.login(request);
    }

    @PostMapping("/register")
    public UserResponse register(@RequestBody UserRequest request) {
        //solo registro de usuario 
        request =new UserRequest(request.nombre(), request.apellido(), request.email(), "USER", request.password());
        return authService.registerUser(request);
    }

    @PostMapping("/admin/register")
    @PreAuthorize("hasRole('ADMIN')")
    public UserResponse registerAdmin(@RequestBody UserRequest request) {
        String role = request.rol() == null || request.rol().isBlank() ? "USER" : request.rol();
        return authService.registerWithRole(request, role);
    }
}