package com.ganado.gateway.controller;

import com.ganado.gateway.dto.reproduccion.GestacionRequest;
import com.ganado.gateway.dto.reproduccion.GestacionResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("/api/reproduccion/gestaciones")
@RequiredArgsConstructor
public class GestacionController {

    private final ReproduccionClient reproduccionClient;

    /** Crear gestación */
    @PostMapping
    public Mono<GestacionResponseDTO> crear(@RequestBody GestacionRequest request) {
        return reproduccionClient.crearGestacion(request);
    }

    /** Listar todas las gestaciones */
    @GetMapping
    public Flux<GestacionResponseDTO> listarTodas() {
        return reproduccionClient.listarTodasGestaciones();
    }

    /** Buscar gestación por ID */
    @GetMapping("/{id}")
    public Mono<GestacionResponseDTO> buscarPorId(@PathVariable UUID id) {
        return reproduccionClient.buscarGestacionPorId(id);
    }

    /** Buscar gestaciones por hembra */
    @GetMapping("/hembra/{idHembra}")
    public Flux<GestacionResponseDTO> buscarPorHembra(@PathVariable UUID idHembra) {
        return reproduccionClient.buscarGestacionesPorHembra(idHembra);
    }

    /** Actualizar gestación */
    @PutMapping("/{id}")
    public Mono<GestacionResponseDTO> actualizar(@PathVariable UUID id,
                                                 @RequestBody GestacionRequest request) {
        return reproduccionClient.actualizarGestacion(id, request);
    }

    /** Eliminar gestación */
    @DeleteMapping("/{id}")
    public Mono<Void> eliminar(@PathVariable UUID id) {
        return reproduccionClient.eliminarGestacion(id);
    }
}