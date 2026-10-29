package com.ganado.reproduccion.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class MontaRequest {
    private UUID idHembra;
    private UUID idMacho;
    private LocalDate fecha;
    private String metodoUtilizado;
    private String notas;
}
