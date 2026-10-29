package com.ganado.gateway.controller;

import com.ganado.gateway.dto.inventory.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("/api/inventory/fincas")
@RequiredArgsConstructor
public class FincaGatewayController {

    private final InventoryClient inventoryClient;
    private final UserClient userClient;

    @PostMapping
    public Mono<FincaResponseDTO> crear(@RequestBody FincaRequestDTO dto) {
        return inventoryClient.crearFinca(dto);
    }

    @GetMapping
    public Flux<FincaResponseDTO> listar() {
        return inventoryClient.listarFincas();
    }

    @GetMapping("/{id}")
    public Mono<FincaResponseDTO> obtener(@PathVariable UUID id) {
        return inventoryClient.obtenerFincaPorId(id);
    }

    @PutMapping("/{id}")
    public Mono<FincaResponseDTO> actualizar(
            @PathVariable UUID id,
            @RequestBody FincaRequestDTO dto
    ) {
        return inventoryClient.actualizarFinca(id, dto);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> eliminar(@PathVariable UUID id) {
        return inventoryClient.eliminarFinca(id);
    }

    @PostMapping("/invitaciones")
    public Mono<InvitationResponseDTO> crearInvitacion(
            @RequestBody InvitationRequestEmailDTO emailDto
    ) {
        return userClient.findByEmail(emailDto.getUsuarioEmail())
                .switchIfEmpty(Mono.error(new RuntimeException("No existe un usuario con ese email")))
                .flatMap(usuario -> {

                    InvitationRequestDTO dto = new InvitationRequestDTO();
                    dto.setUsuarioId(usuario.getId());
                    dto.setFincaId(emailDto.getFincaId());

                    return inventoryClient.crearInvitacion(dto);
                });
    }

    /** Listar todas las invitaciones de la finca (filtrando desde el gateway) */
    @GetMapping("/{fincaId}/invitaciones")
    public Flux<InvitationResponseDTO> listarInvitacionesPorFinca(@PathVariable UUID fincaId) {
        return inventoryClient
                .listarInvitaciones()
                .filter(inv -> inv.getFincaId().equals(fincaId));
    }

    /** Obtener invitación por ID */
    @GetMapping("/invitaciones/{invId}")
    public Mono<InvitationResponseDTO> obtenerInvitacion(@PathVariable UUID invId) {
        return inventoryClient.obtenerInvitacionPorId(invId);
    }

    /** Actualizar invitación */
    @PutMapping("/invitaciones/{invId}")
    public Mono<InvitationResponseDTO> actualizarInvitacion(
            @PathVariable UUID invId,
            @RequestBody InvitationRequestDTO dto
    ) {
        return inventoryClient.actualizarInvitacion(invId, dto);
    }

    /** Decidir invitación (aceptar/rechazar) */
    @PostMapping("/invitaciones/{invId}/decidir")
    public Mono<InvitationResponseDTO> decidirInvitacion(
            @RequestBody InvitationDecisionDTO dto
    ) {
        return inventoryClient.decidirInvitacion(dto);
    }

    /** Eliminar invitación */
    @DeleteMapping("/invitaciones/{invId}")
    public Mono<Void> eliminarInvitacion(@PathVariable UUID invId) {
        return inventoryClient.eliminarInvitacion(invId);
    }

    @GetMapping("/usuario/{usuarioId}")
    public Flux<InvitationResponseDTO> obtenerPorUsuarioId(
            @PathVariable UUID usuarioId
    ) {
        return inventoryClient.obtenerInvitacionesPorUsuarioId(usuarioId);
    }

    /** Obtener solo las invitaciones PENDING del usuario */
    @GetMapping("/usuario/{usuarioId}/pendientes")
    public Flux<InvitationResponseDTO> obtenerPendientesPorUsuario(
            @PathVariable UUID usuarioId
    ) {
        return inventoryClient.obtenerPendientesPorUsuario(usuarioId);
    }
}
