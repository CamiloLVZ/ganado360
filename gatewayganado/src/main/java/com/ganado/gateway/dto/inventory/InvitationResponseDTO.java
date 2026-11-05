package com.ganado.gateway.dto.inventory;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class InvitationResponseDTO {
    private UUID id;
    private UUID usuarioId;
    private UUID fincaId;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime respondedAt;
}
