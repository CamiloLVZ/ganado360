package com.ganado.gateway.dto.reproduccion;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
public class MontaResponseDTO {
    private UUID id;
    private UUID idHembra;
    private UUID idMacho;
    private LocalDate fecha;
    private String metodoUtilizado;
    private String notas;
    private EstadoMonta estado;
}
