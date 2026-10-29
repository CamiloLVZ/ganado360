package com.ganado.reproduccion.dto;

import com.ganado.reproduccion.model.DiagnosticoGestacion;
import lombok.Data;
import org.antlr.v4.runtime.misc.NotNull;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class DiagnosticoGestacionRequest {

    @NotNull
    private UUID idMonta;

    @NotNull
    private LocalDate fecha;

    @NotNull
    private DiagnosticoGestacion.Resultado resultado;

    @NotNull
    private String especie;

    private String observaciones;
}
