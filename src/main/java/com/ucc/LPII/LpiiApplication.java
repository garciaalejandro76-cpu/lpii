package com.ucc.LPII;

import com.ucc.LPII.entity.Usuario;
import com.ucc.LPII.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class LpiiApplication {

    public static void main(String[] args) {
        SpringApplication.run(LpiiApplication.class, args);
    }

    // Este bloque genera de forma segura el usuario administrador de pruebas al iniciar el servidor
    @Bean
    CommandLineRunner init(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            // 1. Limpiamos cualquier registro corrupto previo con este correo
            usuarioRepository.findByCorreo("admin@test.com").ifPresent(usuarioRepository::delete);

            // 2. Instanciamos el administrador de pruebas definitivo
            Usuario admin = new Usuario();
            admin.setNombre("Administrador de Pruebas");
            admin.setCorreo("admin@test.com");
            admin.setEdad(30);
            
            // Encriptación nativa compatible con el mecanismo de autenticación del sistema
            admin.setContrasena(passwordEncoder.encode("admin123")); 
            admin.setRol("ROLE_ADMIN");

            usuarioRepository.save(admin);
            System.out.println("===============================================================");
            System.out.println("¡ADMINISTRADOR DE PRUEBAS ('admin@test.com') CREADO CON ÉXITO!");
            System.out.println("===============================================================");
        };
    }
}