package com.ganado.gateway.model;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {

    private UUID id;

    private String nombre;

    private String email;

    private String password;

    private LocalDateTime fechaCreacion;

    private String resetCode;     // para recuperación
}
