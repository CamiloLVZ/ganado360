package com.ganado.reproduccion.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;


@Entity
@Table(name = "montas")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Monta {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private UUID idHembra;

    @Column(nullable = false)
    private UUID idMacho;

    @Column(nullable = false)
    private LocalDate fecha;

    private String metodoUtilizado;

    private String notas;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoMonta estado = EstadoMonta.ACTIVA; // ACTIVA / FINALIZADA

    public enum EstadoMonta {
        ACTIVA,
        FINALIZADA
    }

}
