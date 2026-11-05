package com.ganado.usuarios.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "usuario")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "usuario_id")
    private UUID id;

    @NotNull(message = "El nombre no puede ser nulo")
    private String nombre;

    @Column(unique = true)
    private String email;

    @NotNull(message = "La clave no puede ser nula")
    private String password;

    @NotNull(message = "La fecha no puede ser nula")
    private LocalDateTime fechaCreacion;

    private String resetCode;     // para recuperación
}