package com.ganado.gateway.dto.reproduccion;


import jakarta.validation.constraints.NotNull;
import lombok.Data;


import java.time.LocalDate;
import java.util.UUID;

@Data
public class DiagnosticoGestacionRequest {

    @NotNull
    private UUID idMonta;

    @NotNull
    private LocalDate fecha;

    @NotNull
    private Resultado resultado;

    @NotNull
    private String especie;

    private String observaciones;
}
