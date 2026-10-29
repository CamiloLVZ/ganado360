package com.ganado.inventario.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Finca {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    private String nombre;

    private String departamento;

    private String municipio;

    private UUID usuarioCreadorId;


    @ElementCollection
    @CollectionTable(
            name = "finca_miembros",
            joinColumns = @JoinColumn(name = "finca_id")
    )
    @Column(name = "usuario_id")
    private List<UUID> usuarioMiembroIds = new ArrayList<>();

    @OneToMany(mappedBy = "finca", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Animal> animales = new ArrayList<>();
}
