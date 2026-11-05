package com.ganado.inventario.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "animal_history")
public class AnimalHistory {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(nullable = false, length = 1000)
    private String descripcion;

    /**
     * Fecha de creación (solo fecha, sin hora).
     */
    @Column(name = "fecha_creacion", nullable = false)
    private LocalDateTime fechaCreacion;

    /**
     * Nombre del día en español (por ejemplo: "lunes").
     */
    @Column(name = "dia_creacion", nullable = false, length = 20)
    private String diaCreacion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "animal_id", nullable = false)
    private Animal animal;

    /**
     * Si no vienen fecha/dia, se generan al persistir.
     */
    @PrePersist
    public void prePersist() {
        if (this.fechaCreacion == null) {
            this.fechaCreacion = LocalDateTime.now();
        }
        if (this.diaCreacion == null) {
            this.diaCreacion = this.fechaCreacion.getDayOfWeek()
                    .getDisplayName(TextStyle.FULL, new Locale("es", "ES"));
            // normalizar a minúsculas
            this.diaCreacion = this.diaCreacion.toLowerCase();
        }
    }
}
