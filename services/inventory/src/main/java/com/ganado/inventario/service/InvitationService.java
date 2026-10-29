package com.ganado.inventario.service;

import com.ganado.inventario.dto.InvitationDecisionDTO;
import com.ganado.inventario.dto.InvitationRequestDTO;
import com.ganado.inventario.dto.InvitationResponseDTO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

public interface InvitationService {
    // -----------------------------
    // CREATE
    // -----------------------------
    InvitationResponseDTO create(InvitationRequestDTO dto);

    // -----------------------------
    // GET ALL
    // -----------------------------
    List<InvitationResponseDTO> findAll();

    // -----------------------------
    // GET BY ID
    // -----------------------------
    InvitationResponseDTO findById(UUID id);

    List<InvitationResponseDTO> findByUsuarioId(UUID usuarioId);

    List<InvitationResponseDTO> obtenerPendientesPorUsuario(UUID usuarioId);

    // -----------------------------
    // UPDATE
    // -----------------------------
    @Transactional
    InvitationResponseDTO update(UUID id, InvitationRequestDTO dto);

    // -----------------------------
    // DELETE
    // -----------------------------
    void delete(UUID id);

    // -----------------------------
    // DECISION (ACEPTAR / RECHAZAR)
    // -----------------------------
    @Transactional
    InvitationResponseDTO decide(InvitationDecisionDTO dto);
}
