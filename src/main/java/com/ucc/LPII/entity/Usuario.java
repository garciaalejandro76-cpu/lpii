package com.ucc.LPII.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "usuarios")
@Getter
@Setter
@NoArgsConstructor
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String nombre;
    
    @Column(nullable = false, unique = true) // Restricción única en base de datos
    private String correo;
    
    private int edad;
    
    @Column(nullable = false)
    private String contrasena;
    
    @Column(nullable = false)
    private String rol; // Ejemplo: "ROLE_USER" o "ROLE_ADMIN"
}