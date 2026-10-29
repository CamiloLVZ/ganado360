package com.ganado.gateway.dto.reproduccion;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class NacimientoRequestDTO {

    @NotNull
    private UUID idMonta;

    @NotNull
    private UUID idMadre;

    @NotNull
    private UUID idAnimal;

    @NotNull
    private LocalDate fecha;

    private String sexo;
    private Double peso;
    private String observaciones;
}
