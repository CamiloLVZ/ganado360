package com.ganado.reproduccion.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "gestaciones")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Gestacion {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)

    private UUID id;

    @Column(nullable = false)
    private UUID idMonta;

    @Column(nullable = false)
    private UUID idHembra;

    @Column(nullable = false)
    private LocalDate fechaInicio;

    @Column(nullable = false)
    private LocalDate fechaEstimadaParto;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoGestacion estado = EstadoGestacion.ACTIVA;

    public enum EstadoGestacion {
        ACTIVA,
        CERRADA
    }

}
