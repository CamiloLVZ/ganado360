package com.ganado.gateway.dto.reproduccion;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class GestacionRequest {

    @NotNull
    private UUID idMonta;

    @NotNull
    private UUID idHembra;

    @NotNull
    private LocalDate fechaInicio;

    @NotNull
    private LocalDate fechaEstimadaParto;

    private String estado; // opcional, default ACTIVA
}
