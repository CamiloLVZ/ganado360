package com.ganado.reproduccion.dto;

import com.ganado.reproduccion.model.DiagnosticoGestacion;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
public class DiagnosticoGestacionResponseDTO {
    private UUID id;
    private UUID idMonta;
    private LocalDate fecha;
    private DiagnosticoGestacion.Resultado resultado;
    private String observaciones;
}
