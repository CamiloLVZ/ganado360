package com.ganado.gateway.dto.inventory;

import lombok.Data;
import java.util.List;
import java.util.UUID;

@Data
public class FincaRequestDTO {
    private String nombre;
    private String departamento;
    private String municipio;

    // ID del creador
    private UUID usuarioCreadorId;

    // Lista opcional de miembros adicionales
    private List<UUID> usuarioMiembroIds;
}
