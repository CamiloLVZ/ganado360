package com.ganado.inventario.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "invitacion")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Invitation {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "invitacion_id", updatable = false, nullable = false)
    private UUID id;

    // ID del usuario invitado (queda como UUID, NO es relación SQL)
    @Column(name = "usuario_id", nullable = false)
    private UUID usuarioId;

    // RELACIÓN SQL → finca
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "finca_id", referencedColumnName = "id")
    private Finca finca;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private InvitationStatus status; // PENDING, ACCEPTED, REJECTED

    @Column(name = "fecha_creacion", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "fecha_respuesta")
    private LocalDateTime respondedAt;
}