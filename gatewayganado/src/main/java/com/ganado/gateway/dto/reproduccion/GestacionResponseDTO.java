package com.ganado.gateway.dto.reproduccion;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
public class GestacionResponseDTO {
    private UUID id;
    private UUID idMonta;
    private UUID idHembra;
    private LocalDate fechaInicio;
    private LocalDate fechaEstimadaParto;
    private EstadoGestacion estado;
}

