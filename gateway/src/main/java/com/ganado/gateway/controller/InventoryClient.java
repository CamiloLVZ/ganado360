package com.ganado.gateway.controller;

import com.ganado.gateway.dto.inventory.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.UUID;

@Component
public class InventoryClient {

    private final WebClient webClient;

    public InventoryClient(WebClient.Builder builder, @Value("${inventory.service.url}") String baseUrl) {
        this.webClient = builder
                .baseUrl(baseUrl) // microservicio de inventario
                .build();
    }

    // -----------------------------
    //  ANIMALES
    // -----------------------------

    public Mono<AnimalResponseDTO> crearAnimal(AnimalRequestDTO dto) {
        return webClient.post()
                .uri("/api/inventory/animales")
                .bodyValue(dto)
                .retrieve()
                .bodyToMono(AnimalResponseDTO.class);
    }

    public Mono<AnimalResponseDTO> obtenerAnimalPorId(UUID id) {
        return webClient.get()
                .uri("/api/inventory/animales/{id}", id)
                .retrieve()
                .bodyToMono(AnimalResponseDTO.class);
    }

    public Flux<AnimalResponseDTO> listarAnimales() {
        return webClient.get()
                .uri("/api/inventory/animales")
                .retrieve()
                .bodyToFlux(AnimalResponseDTO.class);
    }

    public Mono<AnimalResponseDTO> actualizarAnimal(UUID id, AnimalRequestDTO dto) {
        return webClient.put()
                .uri("/api/inventory/animales/{id}", id)
                .bodyValue(dto)
                .retrieve()
                .bodyToMono(AnimalResponseDTO.class);
    }

    public Mono<Void> eliminarAnimal(UUID id) {
        return webClient.delete()
                .uri("/api/inventory/animales/{id}", id)
                .retrieve()
                .bodyToMono(Void.class);
    }

    // -----------------------------
    //  FINCAS
    // -----------------------------

    public Mono<FincaResponseDTO> crearFinca(FincaRequestDTO dto) {
        return webClient.post()
                .uri("/api/inventory/fincas")
                .bodyValue(dto)
                .retrieve()
                .bodyToMono(FincaResponseDTO.class);
    }

    public Mono<FincaResponseDTO> obtenerFincaPorId(UUID id) {
        return webClient.get()
                .uri("/api/inventory/fincas/{id}", id)
                .retrieve()
                .bodyToMono(FincaResponseDTO.class);
    }

    public Flux<FincaResponseDTO> listarFincas() {
        return webClient.get()
                .uri("/api/inventory/fincas")
                .retrieve()
                .bodyToFlux(FincaResponseDTO.class);
    }

    public Mono<FincaResponseDTO> actualizarFinca(UUID id, FincaRequestDTO dto) {
        return webClient.put()
                .uri("/api/inventory/fincas/{id}", id)
                .bodyValue(dto)
                .retrieve()
                .bodyToMono(FincaResponseDTO.class);
    }

    public Mono<Void> eliminarFinca(UUID id) {
        return webClient.delete()
                .uri("/api/inventory/fincas/{id}", id)
                .retrieve()
                .bodyToMono(Void.class);
    }

    public Mono<InvitationResponseDTO> crearInvitacion(InvitationRequestDTO dto) {
        return webClient.post()
                .uri("/api/inventory/invitations")
                .bodyValue(dto)
                .retrieve()
                .bodyToMono(InvitationResponseDTO.class);
    }

    /** Listar invitaciones */
    public Flux<InvitationResponseDTO> listarInvitaciones() {
        return webClient.get()
                .uri("/api/inventory/invitations")
                .retrieve()
                .bodyToFlux(InvitationResponseDTO.class);
    }

    /** Obtener invitación por ID */
    public Mono<InvitationResponseDTO> obtenerInvitacionPorId(UUID id) {
        return webClient.get()
                .uri("/api/inventory/invitations/{id}", id)
                .retrieve()
                .bodyToMono(InvitationResponseDTO.class);
    }

    /** Actualizar invitación */
    public Mono<InvitationResponseDTO> actualizarInvitacion(UUID id, InvitationRequestDTO dto) {
        return webClient.put()
                .uri("/api/inventory/invitations/{id}", id)
                .bodyValue(dto)
                .retrieve()
                .bodyToMono(InvitationResponseDTO.class);
    }

    /** Eliminar invitación */
    public Mono<Void> eliminarInvitacion(UUID id) {
        return webClient.delete()
                .uri("/api/inventory/invitations/{id}", id)
                .retrieve()
                .bodyToMono(Void.class);
    }

    /** Aceptar/Rechazar invitación */
    public Mono<InvitationResponseDTO> decidirInvitacion(InvitationDecisionDTO dto) {
        return webClient.post()
                .uri("/api/inventory/invitations/decide")
                .bodyValue(dto)
                .retrieve()
                .bodyToMono(InvitationResponseDTO.class);
    }

    public Flux<InvitationResponseDTO> obtenerInvitacionesPorUsuarioId(UUID usuarioId) {
        return webClient.get()
                .uri("/api/inventory/invitations/userid/{id}", usuarioId)
                .retrieve()
                .bodyToFlux(InvitationResponseDTO.class);
    }

    /** Obtener solo las invitaciones PENDING de un usuario */
    public Flux<InvitationResponseDTO> obtenerPendientesPorUsuario(UUID usuarioId) {
        return webClient.get()
                .uri("/api/inventory/invitations/usuario/{usuarioId}/pendientes", usuarioId)
                .retrieve()
                .bodyToFlux(InvitationResponseDTO.class);
    }

    public Mono<AnimalHistoryResponseDTO> crearAnimalHistory(AnimalHistoryRequestDTO dto) {
        return webClient.post()
                .uri("/api/inventory/animales/historias")
                .bodyValue(dto)
                .retrieve()
                .bodyToMono(AnimalHistoryResponseDTO.class);
    }

    public Flux<AnimalHistoryResponseDTO> listarAnimalHistory() {
        return webClient.get()
                .uri("/api/inventory/animales/historias")
                .retrieve()
                .bodyToFlux(AnimalHistoryResponseDTO.class);
    }

    public Mono<AnimalHistoryResponseDTO> obtenerAnimalHistoryPorId(UUID id) {
        return webClient.get()
                .uri("/api/inventory/animales/historias/{id}", id)
                .retrieve()
                .bodyToMono(AnimalHistoryResponseDTO.class);
    }

    public Flux<AnimalHistoryResponseDTO> listarAnimalHistoryPorAnimal(UUID animalId) {
        return webClient.get()
                .uri("/api/inventory/animales/historias/animal/{animalId}", animalId)
                .retrieve()
                .bodyToFlux(AnimalHistoryResponseDTO.class);
    }

    public Mono<AnimalHistoryResponseDTO> actualizarAnimalHistory(UUID id, AnimalHistoryRequestDTO dto) {
        return webClient.put()
                .uri("/api/inventory/animales/historias/{id}", id)
                .bodyValue(dto)
                .retrieve()
                .bodyToMono(AnimalHistoryResponseDTO.class);
    }

    public Mono<Void> eliminarAnimalHistory(UUID id) {
        return webClient.delete()
                .uri("/api/inventory/animales/historias/{id}", id)
                .retrieve()
                .bodyToMono(Void.class);
    }

    public Mono<byte[]> exportarHistorialAnimal(UUID animalId) {
        return webClient.get()
                .uri("/api/inventory/animales/historias/animal/{animalId}/export/pdf", animalId)
                .retrieve()
                .bodyToMono(byte[].class);
    }

    public Flux<AnimalResponseDTO> obtenerPorEspecie(@PathVariable String especie){
        return webClient.get()
                .uri("/api/inventory/animales/especie/{especie}", especie)
                .retrieve()
                .bodyToFlux(AnimalResponseDTO.class);

    }

}
