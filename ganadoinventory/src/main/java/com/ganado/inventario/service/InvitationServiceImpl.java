package com.ganado.inventario.service;

import com.ganado.inventario.dto.InvitationDecisionDTO;
import com.ganado.inventario.dto.InvitationRequestDTO;
import com.ganado.inventario.dto.InvitationResponseDTO;
import com.ganado.inventario.mapper.InvitationMapper;
import com.ganado.inventario.model.Finca;
import com.ganado.inventario.model.Invitation;
import com.ganado.inventario.model.InvitationStatus;
import com.ganado.inventario.repository.FincaRepository;
import com.ganado.inventario.repository.InvitationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InvitationServiceImpl implements InvitationService {

    private final InvitationRepository repo;
    private final FincaRepository fincaRepo;

    // -----------------------------
    // CREATE
    // -----------------------------
    @Override
    public InvitationResponseDTO create(InvitationRequestDTO dto) {

        Finca finca = fincaRepo.findById(dto.getFincaId())
                .orElseThrow(() -> new RuntimeException("Finca no encontrada"));

        Invitation inv = InvitationMapper.toEntity(dto, finca);

        Invitation saved = repo.save(inv);
        return InvitationMapper.toDTO(saved);
    }

    // -----------------------------
    // GET ALL
    // -----------------------------
    @Override
    public List<InvitationResponseDTO> findAll() {
        return repo.findAll().stream()
                .map(InvitationMapper::toDTO)
                .toList();
    }

    // -----------------------------
    // GET BY ID
    // -----------------------------
    @Override
    public InvitationResponseDTO findById(UUID id) {
        Invitation inv = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Invitación no encontrada"));

        return InvitationMapper.toDTO(inv);
    }

    @Override
    public List<InvitationResponseDTO> findByUsuarioId(UUID usuarioId) {
        List<Invitation> invitaciones = repo.findByUsuarioId(usuarioId);

        return invitaciones.stream()
                .map(InvitationMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<InvitationResponseDTO> obtenerPendientesPorUsuario(UUID usuarioId) {
        return repo.findByUsuarioIdAndStatus(usuarioId, InvitationStatus.PENDING)
                .stream()
                .map(InvitationMapper::toDTO)
                .toList();
    }

    // -----------------------------
    // UPDATE
    // -----------------------------
    @Transactional
    @Override
    public InvitationResponseDTO update(UUID id, InvitationRequestDTO dto) {

        Invitation inv = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Invitación no encontrada"));

        // Actualizar campos editables
        inv.setUsuarioId(dto.getUsuarioId());

        // actualizar finca si cambió
        if (dto.getFincaId() != null) {
            Finca finca = fincaRepo.findById(dto.getFincaId())
                    .orElseThrow(() -> new RuntimeException("Finca no encontrada"));
            inv.setFinca(finca);
        }

        return InvitationMapper.toDTO(inv);
    }

    // -----------------------------
    // DELETE
    // -----------------------------
    @Override
    public void delete(UUID id) {
        if (!repo.existsById(id)) {
            throw new RuntimeException("Invitación no encontrada");
        }
        repo.deleteById(id);
    }

    // -----------------------------
    // DECISION (ACEPTAR / RECHAZAR)
    // -----------------------------
    @Transactional
    @Override
    public InvitationResponseDTO decide(InvitationDecisionDTO dto) {

        Invitation inv = repo.findById(dto.getId())
                .orElseThrow(() -> new RuntimeException("Invitación no encontrada"));

        if (inv.getStatus() != InvitationStatus.PENDING) {
            throw new RuntimeException("La invitación ya fue respondida.");
        }

        inv.setRespondedAt(LocalDateTime.now());

        // ----- ACEPTAR -----
        if (dto.isAccepted()) {

            inv.setStatus(InvitationStatus.ACCEPTED);
            repo.save(inv);

            Finca finca = inv.getFinca(); // ✔ ya no es necesario buscarla por ID

            List<UUID> miembros = finca.getUsuarioMiembroIds();
            if (miembros == null) {
                miembros = new ArrayList<>();
            }

            // Agregar usuario si no está
            if (!miembros.contains(inv.getUsuarioId())) {
                miembros.add(inv.getUsuarioId());
                finca.setUsuarioMiembroIds(miembros);
                fincaRepo.save(finca);
            }

        }
        // ----- RECHAZAR -----
        else {
            inv.setStatus(InvitationStatus.REJECTED);
            repo.save(inv);
        }

        return InvitationMapper.toDTO(inv);
    }
}