package com.ganado.gateway.dto.inventory;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class AnimalHistoryResponseDTO {
    private UUID id;
    private UUID animalId;
    private String descripcion;
    private LocalDateTime fechaCreacion;
    private String diaCreacion;
}
