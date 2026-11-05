package com.ganado.gateway.controller;

import com.ganado.gateway.dto.sanidad.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("/api/sanidad/refuerzos")
@RequiredArgsConstructor
public class RefuerzoController {

    private final SanidadClient sanidadProductosRefuerzosClient;

    // ========================================
    // REFUERZOS
    // ========================================

    /**
     * Crear refuerzo
     */
    @PostMapping
    public Mono<RefuerzoResponseDTO> crear(@RequestBody RefuerzoRequestDTO dto) {
        return sanidadProductosRefuerzosClient.crearRefuerzo(dto);
    }

    /**
     * Listar todos los refuerzos
     */
    @GetMapping
    public Flux<RefuerzoResponseDTO> listar() {
        return sanidadProductosRefuerzosClient.listarRefuerzos();
    }

    /**
     * Obtener refuerzo por ID
     */
    @GetMapping("/{id}")
    public Mono<RefuerzoResponseDTO> obtener(@PathVariable UUID id) {
        return sanidadProductosRefuerzosClient.obtenerRefuerzoPorId(id);
    }

    /**
     * Actualizar refuerzo
     */
    @PutMapping("/{id}")
    public Mono<RefuerzoResponseDTO> actualizar(@PathVariable UUID id,
                                                @RequestBody RefuerzoRequestDTO dto) {
        return sanidadProductosRefuerzosClient.editarRefuerzo(id, dto);
    }

    /**
     * Eliminar refuerzo
     */
    @DeleteMapping("/{id}")
    public Mono<Void> eliminar(@PathVariable UUID id) {
        return sanidadProductosRefuerzosClient.eliminarRefuerzo(id);
    }

    /**
     * Obtener refuerzos por vacuna
     */
    @GetMapping("/vacuna/{vacunaId}")
    public Flux<RefuerzoResponseDTO> obtenerPorVacuna(@PathVariable UUID vacunaId) {
        return sanidadProductosRefuerzosClient.obtenerRefuerzosPorVacuna(vacunaId);
    }
}