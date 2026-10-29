package com.ganado.gateway.dto.reproduccion;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
public class NacimientoResponseDTO {

    private UUID id;
    private UUID idMonta;
    private UUID idMadre;
    private UUID idAnimal;
    private LocalDate fecha;
    private String sexo;
    private Double peso;
    private String observaciones;
}
