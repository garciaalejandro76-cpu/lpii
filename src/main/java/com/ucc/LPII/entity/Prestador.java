package com.ucc.LPII.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "prestadores")
@Getter
@Setter
@NoArgsConstructor
public class Prestador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String nombre;

    // Mapeo del tipo ENUM de SQL como un String en Java usando @Enumerated
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoPrestador tipo;

    @Column(length = 100)
    private String categoria;

    @Column(length = 50)
    private String telefono;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    // Relación Muchos a Uno con Zona
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "zona_id", nullable = false)
    private Zona zona;

    // Relación Uno a Uno con Ubicacion
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ubicacion_id", referencedColumnName = "id", nullable = false, unique = true)
    private Ubicacion ubicacion;

    // Enumeración para representar las opciones fijas de la base de datos
    public enum TipoPrestador {
        Hospedaje,
        Comercio
    }
}