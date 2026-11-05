package com.ganado.reproduccion.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "nacimientos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Nacimiento {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(nullable = false)
    private UUID idMonta;

    @Column(nullable = false)
    private UUID idMadre;

    @Column(nullable = false)
    private UUID idAnimal;

    @Column(nullable = false)
    private LocalDate fecha;

    private String sexo;

    private Double peso;

    private String observaciones;
}
