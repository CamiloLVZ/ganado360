package com.ganado.gateway.controller;

import com.ganado.gateway.dto.reproduccion.DiagnosticoGestacionRequest;
import com.ganado.gateway.dto.reproduccion.DiagnosticoGestacionResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("/api/reproduccion/diagnosticos")
@RequiredArgsConstructor
public class DiagnosticoGestacionController {

    private final ReproduccionClient reproduccionClient;

    /** Registrar diagnóstico de gestación */
    @PostMapping
    public Mono<DiagnosticoGestacionResponseDTO> registrar(@RequestBody DiagnosticoGestacionRequest request) {
        return reproduccionClient.registrarDiagnostico(request);
    }

    /** Listar todos los diagnósticos */
    @GetMapping
    public Flux<DiagnosticoGestacionResponseDTO> listarTodos() {
        return reproduccionClient.listarTodosDiagnosticos();
    }

    /** Obtener diagnóstico por ID */
    @GetMapping("/{id}")
    public Mono<DiagnosticoGestacionResponseDTO> obtenerPorId(@PathVariable UUID id) {
        return reproduccionClient.obtenerDiagnosticoPorId(id);
    }

    /** Obtener diagnósticos por monta */
    @GetMapping("/monta/{idMonta}")
    public Flux<DiagnosticoGestacionResponseDTO> obtenerPorMonta(@PathVariable UUID idMonta) {
        return reproduccionClient.obtenerDiagnosticosPorMonta(idMonta);
    }

    /** Actualizar diagnóstico */
    @PutMapping("/{id}")
    public Mono<DiagnosticoGestacionResponseDTO> actualizar(@PathVariable UUID id,
                                                            @RequestBody DiagnosticoGestacionRequest request) {
        return reproduccionClient.actualizarDiagnostico(id, request);
    }

    /** Eliminar diagnóstico */
    @DeleteMapping("/{id}")
    public Mono<Void> eliminar(@PathVariable UUID id) {
        return reproduccionClient.eliminarDiagnostico(id);
    }
}