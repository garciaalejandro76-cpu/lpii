package com.ucc.LPII.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "lugares")
@Getter
@Setter
@NoArgsConstructor
public class Lugar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String nombre;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    // Relación Muchos a Uno: Varios lugares pertenecen a una misma zona
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "zona_id", nullable = false)
    private Zona zona;

    // Relación Uno a Uno: Un lugar tiene una ubicación única
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ubicacion_id", referencedColumnName = "id", nullable = false, unique = true)
    private Ubicacion ubicacion;
}