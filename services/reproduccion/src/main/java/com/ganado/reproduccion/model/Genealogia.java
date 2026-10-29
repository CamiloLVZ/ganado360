package com.ganado.reproduccion.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "genealogias")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Genealogia {

    // Usamos "hijo" como PK para no añadir otro atributo que no pediste.
    @Id
    @Column(name = "hijo", nullable = false, columnDefinition = "binary(16)")
    private UUID hijo;

    @Column(name = "madre", nullable = false, columnDefinition = "binary(16)")
    private UUID madre;

    // Padre puede ser null si no se registra
    @Column(name = "padre", nullable = true, columnDefinition = "binary(16)")
    private UUID padre;
}
