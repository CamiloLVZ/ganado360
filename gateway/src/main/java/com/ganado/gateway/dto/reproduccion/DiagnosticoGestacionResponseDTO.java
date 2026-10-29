package com.ganado.gateway.dto.reproduccion;


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
    private Resultado resultado;
    private String observaciones;
}
