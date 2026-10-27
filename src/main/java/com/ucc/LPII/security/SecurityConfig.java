package com.ucc.LPII.security;

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

@Configuration
@EnableWebSecurity
@EnableMethodSecurity // Permite usar @PreAuthorize en los métodos de tus Services
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Deshabilitado para APIs RESTful
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // API sin sesión
            .authorizeHttpRequests(auth -> auth
                // 1. Endpoint Público de registro (POST /api/usuarios/registro)
                .requestMatchers(HttpMethod.POST, "/api/usuarios/registro").permitAll()
                
                // 2. Endpoint del listado general (GET /api/usuarios) protegido por Rol/Autoridad
                // Usamos 'hasAuthority' para que valide de manera exacta el string 'ROLE_ADMIN' de tu base de datos
                .requestMatchers(HttpMethod.GET, "/api/usuarios").hasAuthority("ROLE_ADMIN")
                
                // 3. Consultas públicas de tu dominio (ejemplo: ver lugares públicos sin estar logueado)
                .requestMatchers(HttpMethod.GET, "/api/lugares/**").permitAll()
                
                // 4. Cualquier otra petición REST del sistema requiere estar autenticado
                .anyRequest().authenticated()
            )
            .httpBasic(Customizer.withDefaults()); // Habilita Basic Auth
            
        return http.build();
    }
}