package com.ganado.gateway.dto.inventory;

import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class AnimalResponseDTO {
    private UUID id;
    private UUID fincaId;
    private String especie;
    private String raza;
    private String sexo;
    private LocalDate fechaNacimiento;
    private Double peso;
    private String ubicacion;
    private String estado;
    
    
    private String identificador;
}

