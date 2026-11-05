package com.ganado.gateway.dto.inventory;

import lombok.Data;

import java.util.UUID;

@Data
public class InvitationDecisionDTO {
    private UUID id;
    private boolean accepted;
}