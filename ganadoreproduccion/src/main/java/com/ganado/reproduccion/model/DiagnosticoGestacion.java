package com.ganado.reproduccion.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "diagnosticos_gestacion")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DiagnosticoGestacion {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private UUID idMonta;

    @Column(nullable = false)
    private LocalDate fecha;

    private String observaciones;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Resultado resultado; // GESTANTE / VACIA / NO_CONCLUYENTE

    public enum Resultado {
        GESTANTE,
        VACIA,
        NO_CONCLUYENTE
    }
}