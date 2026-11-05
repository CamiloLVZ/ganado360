package com.ganado.gateway.controller;

import com.ganado.gateway.dto.reproduccion.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
public class ReproduccionClient {

    private final WebClient webClient;

    public ReproduccionClient(WebClient.Builder builder, @Value("${reproduccion.service.url}") String baseUrl) {
        this.webClient = builder
                .baseUrl(baseUrl) // microservicio de reproducción
                .build();
    }

    // -----------------------------
    //  DIAGNÓSTICOS DE GESTACIÓN
    // -----------------------------

    /**
     * Registrar un diagnóstico de gestación
     */
    public Mono<DiagnosticoGestacionResponseDTO> registrarDiagnostico(DiagnosticoGestacionRequest request) {
        return webClient.post()
                .uri("/api/diagnosticos")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(DiagnosticoGestacionResponseDTO.class);
    }

    /**
     * Listar todos los diagnósticos de gestación
     */
    public Flux<DiagnosticoGestacionResponseDTO> listarTodosDiagnosticos() {
        return webClient.get()
                .uri("/api/diagnosticos")
                .retrieve()
                .bodyToFlux(DiagnosticoGestacionResponseDTO.class);
    }

    /**
     * Obtener diagnóstico de gestación por ID
     */
    public Mono<DiagnosticoGestacionResponseDTO> obtenerDiagnosticoPorId(UUID id) {
        return webClient.get()
                .uri("/api/diagnosticos/{id}", id)
                .retrieve()
                .bodyToMono(DiagnosticoGestacionResponseDTO.class);
    }

    /**
     * Obtener diagnósticos por monta
     */
    public Flux<DiagnosticoGestacionResponseDTO> obtenerDiagnosticosPorMonta(UUID idMonta) {
        return webClient.get()
                .uri("/api/diagnosticos/monta/{idMonta}", idMonta)
                .retrieve()
                .bodyToFlux(DiagnosticoGestacionResponseDTO.class);
    }

    /**
     * Actualizar diagnóstico de gestación
     */
    public Mono<DiagnosticoGestacionResponseDTO> actualizarDiagnostico(UUID id, DiagnosticoGestacionRequest request) {
        return webClient.put()
                .uri("/api/diagnosticos/{id}", id)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(DiagnosticoGestacionResponseDTO.class);
    }

    /**
     * Eliminar diagnóstico de gestación
     */
    public Mono<Void> eliminarDiagnostico(UUID id) {
        return webClient.delete()
                .uri("/api/diagnosticos/{id}", id)
                .retrieve()
                .bodyToMono(Void.class);
    }

    // -----------------------------
    //  GESTACIONES
    // -----------------------------

    /**
     * Crear una gestación
     */
    public Mono<GestacionResponseDTO> crearGestacion(GestacionRequest request) {
        return webClient.post()
                .uri("/api/gestaciones")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(GestacionResponseDTO.class);
    }

    /**
     * Listar todas las gestaciones
     */
    public Flux<GestacionResponseDTO> listarTodasGestaciones() {
        return webClient.get()
                .uri("/api/gestaciones")
                .retrieve()
                .bodyToFlux(GestacionResponseDTO.class);
    }

    /**
     * Buscar gestación por ID
     */
    public Mono<GestacionResponseDTO> buscarGestacionPorId(UUID id) {
        return webClient.get()
                .uri("/api/gestaciones/{id}", id)
                .retrieve()
                .bodyToMono(GestacionResponseDTO.class);
    }

    /**
     * Buscar gestaciones por hembra
     */
    public Flux<GestacionResponseDTO> buscarGestacionesPorHembra(UUID idHembra) {
        return webClient.get()
                .uri("/api/gestaciones/hembra/{idHembra}", idHembra)
                .retrieve()
                .bodyToFlux(GestacionResponseDTO.class);
    }

    /**
     * Actualizar gestación
     */
    public Mono<GestacionResponseDTO> actualizarGestacion(UUID id, GestacionRequest request) {
        return webClient.put()
                .uri("/api/gestaciones/{id}", id)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(GestacionResponseDTO.class);
    }

    /**
     * Eliminar gestación
     */
    public Mono<Void> eliminarGestacion(UUID id) {
        return webClient.delete()
                .uri("/api/gestaciones/{id}", id)
                .retrieve()
                .bodyToMono(Void.class);
    }

    // -----------------------------
    //  MONTAS
    // -----------------------------

    /**
     * Crear una monta
     */
    public Mono<MontaResponseDTO> crearMonta(MontaRequest request) {
        return webClient.post()
                .uri("/api/montas")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(MontaResponseDTO.class);
    }

    /**
     * Obtener monta por ID
     */
    public Mono<MontaResponseDTO> obtenerMontaPorId(UUID id) {
        return webClient.get()
                .uri("/api/montas/{id}", id)
                .retrieve()
                .bodyToMono(MontaResponseDTO.class);
    }

    /**
     * Obtener todas las montas
     */
    public Flux<MontaResponseDTO> obtenerTodasMontas() {
        return webClient.get()
                .uri("/api/montas")
                .retrieve()
                .bodyToFlux(MontaResponseDTO.class);
    }

    /**
     * Actualizar monta
     */
    public Mono<MontaResponseDTO> actualizarMonta(UUID id, MontaRequest request) {
        return webClient.put()
                .uri("/api/montas/{id}", id)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(MontaResponseDTO.class);
    }

    /**
     * Eliminar monta
     */
    public Mono<Void> eliminarMonta(UUID id) {
        return webClient.delete()
                .uri("/api/montas/{id}", id)
                .retrieve()
                .bodyToMono(Void.class);
    }

    // -----------------------------
    //  NACIMIENTOS
    // -----------------------------

    /**
     * Crear un nacimiento
     */
    public Mono<NacimientoResponseDTO> crearNacimiento(NacimientoRequestDTO request) {
        return webClient.post()
                .uri("/api/nacimientos")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(NacimientoResponseDTO.class);
    }

    /**
     * Obtener todos los nacimientos
     */
    public Flux<NacimientoResponseDTO> obtenerTodosNacimientos() {
        return webClient.get()
                .uri("/api/nacimientos")
                .retrieve()
                .bodyToFlux(NacimientoResponseDTO.class);
    }

    /**
     * Obtener nacimiento por ID
     */
    public Mono<NacimientoResponseDTO> obtenerNacimientoPorId(UUID id) {
        return webClient.get()
                .uri("/api/nacimientos/{id}", id)
                .retrieve()
                .bodyToMono(NacimientoResponseDTO.class);
    }

    /**
     * Obtener nacimientos por ID de madre
     */
    public Flux<NacimientoResponseDTO> obtenerNacimientosPorIdMadre(UUID idMadre) {
        return webClient.get()
                .uri("/api/nacimientos/madre/{idMadre}", idMadre)
                .retrieve()
                .bodyToFlux(NacimientoResponseDTO.class);
    }

    /**
     * Actualizar nacimiento
     */
    public Mono<NacimientoResponseDTO> actualizarNacimiento(UUID id, NacimientoRequestDTO request) {
        return webClient.put()
                .uri("/api/nacimientos/{id}", id)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(NacimientoResponseDTO.class);
    }

    /**
     * Eliminar nacimiento
     */
    public Mono<Void> eliminarNacimiento(UUID id) {
        return webClient.delete()
                .uri("/api/nacimientos/{id}", id)
                .retrieve()
                .bodyToMono(Void.class);
    }

    public Mono<GenealogiaResponseDTO> crearGenealogia(GenealogiaRequestDTO request) {
        return webClient.post()
                .uri("/api/genealogias")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(GenealogiaResponseDTO.class);
    }

    /**
     * Listar todas las genealogías
     */
    public Flux<GenealogiaResponseDTO> listarTodasGenealogias() {
        return webClient.get()
                .uri("/api/genealogias")
                .retrieve()
                .bodyToFlux(GenealogiaResponseDTO.class);
    }

    /**
     * Obtener genealogía por hijo (hijoId es la PK lógica)
     */
    public Mono<GenealogiaResponseDTO> obtenerGenealogiaPorHijo(UUID hijoId) {
        return webClient.get()
                .uri("/api/genealogias/{hijoId}", hijoId)
                .retrieve()
                .bodyToMono(GenealogiaResponseDTO.class);
    }

    /**
     * Obtener genealogías por madre
     */
    public Flux<GenealogiaResponseDTO> obtenerGenealogiasPorMadre(UUID madreId) {
        return webClient.get()
                .uri("/api/genealogias/madre/{madreId}", madreId)
                .retrieve()
                .bodyToFlux(GenealogiaResponseDTO.class);
    }

    /**
     * Actualizar genealogía (busca por hijoId)
     */
    public Mono<GenealogiaResponseDTO> actualizarGenealogia(UUID hijoId, GenealogiaRequestDTO request) {
        return webClient.put()
                .uri("/api/genealogias/{hijoId}", hijoId)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(GenealogiaResponseDTO.class);
    }

    /**
     * Eliminar genealogía por hijoId
     */
    public Mono<Void> eliminarGenealogia(UUID hijoId) {
        return webClient.delete()
                .uri("/api/genealogias/{hijoId}", hijoId)
                .retrieve()
                .bodyToMono(Void.class);
    }
}