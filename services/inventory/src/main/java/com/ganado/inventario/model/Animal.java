package com.ganado.inventario.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Animal {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(nullable = false)
    private String especie;

    @Column(nullable = false)
    private String raza;

    @Column(nullable = false)
    private String sexo;

    @Column(nullable = false)
    private LocalDate fechaNacimiento;


    private Double peso;

    @Column(name = "identificador", length = 100, nullable = false)
    private String identificador;

    private String ubicacion;

    // Cada animal pertenece a una finca
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "finca_id", nullable = false)
    private Finca finca;

    @Builder.Default
    @Column(nullable = false, columnDefinition = "varchar(8) default 'Activo'")
    private String estado = "Activo";
}