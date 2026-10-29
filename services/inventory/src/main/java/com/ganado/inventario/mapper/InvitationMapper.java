package com.ganado.inventario.mapper;

import com.ganado.inventario.dto.InvitationRequestDTO;
import com.ganado.inventario.dto.InvitationResponseDTO;
import com.ganado.inventario.model.Finca;
import com.ganado.inventario.model.Invitation;
import com.ganado.inventario.model.InvitationStatus;

import java.time.LocalDateTime;

public class InvitationMapper {

    public static Invitation toEntity(InvitationRequestDTO dto, Finca finca) {
        return Invitation.builder()
                .usuarioId(dto.getUsuarioId())
                .finca(finca)
                .status(InvitationStatus.PENDING)
                .createdAt(LocalDateTime.now())
                .build();
    }

    public static InvitationResponseDTO toDTO(Invitation inv) {
        return InvitationResponseDTO.builder()
                .id(inv.getId())
                .usuarioId(inv.getUsuarioId())
                .fincaId(inv.getFinca().getId())
                .status(inv.getStatus().name())
                .createdAt(inv.getCreatedAt())
                .respondedAt(inv.getRespondedAt())
                .build();
    }
}
