package com.ganado.reproduccion.dto;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDate;
import java.util.UUID;
import com.ganado.reproduccion.model.Gestacion;

@Data
@Builder
public class GestacionResponseDTO {
    private UUID id;
    private UUID idMonta;
    private UUID idHembra;
    private LocalDate fechaInicio;
    private LocalDate fechaEstimadaParto;
    private Gestacion.EstadoGestacion estado;
}
