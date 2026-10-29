package com.ganado.reproduccion.dto;

import lombok.Data;
import org.antlr.v4.runtime.misc.NotNull;

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
