package com.ganado.inventario.dto;

import lombok.Data;
import java.util.UUID;

@Data
public class InvitationRequestDTO {
    private UUID usuarioId;
    private UUID fincaId;
}