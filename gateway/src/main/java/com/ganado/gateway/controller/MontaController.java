package com.ganado.gateway.controller;

import com.ganado.gateway.dto.reproduccion.MontaRequest;
import com.ganado.gateway.dto.reproduccion.MontaResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("/api/reproduccion/montas")
@RequiredArgsConstructor
public class MontaController {

    private final ReproduccionClient reproduccionClient;

    /** Crear monta */
    @PostMapping
    public Mono<MontaResponseDTO> crear(@RequestBody MontaRequest request) {
        return reproduccionClient.crearMonta(request);
    }

    /** Obtener monta por ID */
    @GetMapping("/{id}")
    public Mono<MontaResponseDTO> obtenerPorId(@PathVariable UUID id) {
        return reproduccionClient.obtenerMontaPorId(id);
    }

    /** Obtener todas las montas */
    @GetMapping
    public Flux<MontaResponseDTO> obtenerTodas() {
        return reproduccionClient.obtenerTodasMontas();
    }

    /** Actualizar monta */
    @PutMapping("/{id}")
    public Mono<MontaResponseDTO> actualizar(@PathVariable UUID id,
                                             @RequestBody MontaRequest request) {
        return reproduccionClient.actualizarMonta(id, request);
    }

    /** Eliminar monta */
    @DeleteMapping("/{id}")
    public Mono<Void> eliminar(@PathVariable UUID id) {
        return reproduccionClient.eliminarMonta(id);
    }
}