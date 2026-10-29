package com.ganado.reproduccion.dto;

import lombok.Data;
import org.antlr.v4.runtime.misc.NotNull;

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
