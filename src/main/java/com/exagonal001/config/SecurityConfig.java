package com.exagonal001.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.exagonal001.security.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .cors(Customizer.withDefaults())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                    .requestMatchers(ListaAuth.RUTAS_PUBLICAS_SIN_AUTENTICACION)
                    .permitAll()
                    .requestMatchers(ListaAuth.RUTAS_SWAGGER_PUBLICAS)
                        .permitAll()
                    .requestMatchers(HttpMethod.GET, ListaAuth.RUTAS_AUTORES_GET_AUTENTICADAS).authenticated()
                    .requestMatchers(HttpMethod.GET, ListaAuth.RUTAS_POST_USUARIO_Y_ADMIN).hasAnyRole("USER", "ADMIN")
                    .requestMatchers(HttpMethod.POST, ListaAuth.RUTAS_POST_SOLO_ADMIN).hasRole("ADMIN")
                    .requestMatchers(HttpMethod.GET, ListaAuth.RUTAS_GET_SOLO_ADMIN).hasRole("ADMIN")
                        .anyRequest().authenticated())
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
