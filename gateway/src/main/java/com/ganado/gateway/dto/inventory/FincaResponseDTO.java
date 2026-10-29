package com.ganado.gateway.dto.inventory;
import lombok.Builder;
import lombok.Data;
import java.util.List;
import java.util.UUID;

@Data
@Builder
public class FincaResponseDTO {
    private UUID id;
    private String nombre;
    private String departamento;
    private String municipio;
    private UUID usuarioCreadorId;
    private List<UUID> usuarioMiembroIds;
}
