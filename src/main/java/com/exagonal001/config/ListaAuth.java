package com.exagonal001.config;

public final class ListaAuth {

    private ListaAuth() {
    }

    public static final String[] RUTAS_SWAGGER_PUBLICAS = {
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/swagger-ui.html"
    };

    public static final String[] RUTAS_PUBLICAS_SIN_AUTENTICACION = {
            "/auth/**"
    };

    public static final String[] RUTAS_AUTORES_GET_AUTENTICADAS = {
            "/autores/**"
    };

    public static final String[] RUTAS_POST_SOLO_ADMIN = {
            "/autores/**",
            "/libros/**",
            "/user/**"
    };

    public static final String[] RUTAS_POST_USUARIO_Y_ADMIN = {
            "/prestamos/**"
    };

    public static final String[] RUTAS_GET_SOLO_ADMIN = {
            "/user/**"
    };
}