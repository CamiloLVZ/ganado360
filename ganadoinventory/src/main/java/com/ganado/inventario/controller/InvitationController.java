package com.ganado.inventario.controller;

import com.ganado.inventario.dto.InvitationDecisionDTO;
import com.ganado.inventario.dto.InvitationRequestDTO;
import com.ganado.inventario.dto.InvitationResponseDTO;
import com.ganado.inventario.service.InvitationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/inventory/invitations")
@RequiredArgsConstructor
public class InvitationController {

    private final InvitationService service;

    @PostMapping
    public ResponseEntity<InvitationResponseDTO> create(@RequestBody InvitationRequestDTO dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @GetMapping
    public ResponseEntity<List<InvitationResponseDTO>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<InvitationResponseDTO> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("/userid/{id}")
    public ResponseEntity<List<InvitationResponseDTO>> findByUsuarioId(@PathVariable UUID usuarioId) {
        return ResponseEntity.ok(service.findByUsuarioId(usuarioId));
    }

    @GetMapping("/usuario/{usuarioId}/pendientes")
    public List<InvitationResponseDTO> obtenerPendientesPorUsuario(
            @PathVariable UUID usuarioId
    ) {
        return service.obtenerPendientesPorUsuario(usuarioId);
    }

    @PutMapping("/{id}")
    public ResponseEntity<InvitationResponseDTO> update(@PathVariable UUID id,
                                                        @RequestBody InvitationRequestDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.ok(Map.of("message","Invitación eliminada"));
    }

    // Endpoint para aceptar/rechazar
    @PostMapping("/decide")
    public ResponseEntity<InvitationResponseDTO> decide(@RequestBody InvitationDecisionDTO dto) {
        return ResponseEntity.ok(service.decide(dto));
    }
}