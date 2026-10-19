package com.ucc.LPII.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Entity
@Table(name = "destinos")
@Getter
@Setter
@NoArgsConstructor
public class Destino {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    @Column(length = 100)
    private String pais;

    // Relación bidireccional: Un destino puede tener muchas zonas asociadas
    // El cascade = CascadeType.ALL emula el ON DELETE CASCADE de tu SQL original
    @OneToMany(mappedBy = "destino", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Zona> zonas;
}