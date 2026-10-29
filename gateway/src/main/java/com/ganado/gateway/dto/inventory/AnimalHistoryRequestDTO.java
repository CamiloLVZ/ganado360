package com.ganado.gateway.dto.inventory;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class AnimalHistoryRequestDTO {
    private UUID animalId;
    private String descripcion;
    private LocalDateTime fechaCreacion;
}