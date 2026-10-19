package com.ucc.LPII.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;

@Entity
@Table(name = "ubicaciones")
@Getter
@Setter
@NoArgsConstructor
public class Ubicacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Se usa BigDecimal para mapear correctamente el tipo DECIMAL de SQL sin perder precisión
    @Column(nullable = false, precision = 10, scale = 8)
    private BigDecimal latitud;

    @Column(nullable = false, precision = 11, scale = 8)
    private BigDecimal longitud;

    @Column(length = 255)
    private String referencia;

    // Relación bidireccional opcional con Lugar
    @OneToOne(mappedBy = "ubicacion", fetch = FetchType.LAZY)
    private Lugar lugar;
}