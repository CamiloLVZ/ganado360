package com.ganado.gateway.dto.inventory;

import lombok.Data;

import java.util.UUID;

@Data
public class InvitationRequestEmailDTO {
    private String usuarioEmail;
    private UUID fincaId;
}