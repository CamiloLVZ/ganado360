package com.ganado.gateway.controller;

import com.ganado.gateway.dto.reproduccion.NacimientoRequestDTO;
import com.ganado.gateway.dto.reproduccion.NacimientoResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("/api/reproduccion/nacimientos")
@RequiredArgsConstructor
public class NacimientoController {

    private final ReproduccionClient reproduccionClient;

    /** Crear nacimiento */
    @PostMapping
    public Mono<NacimientoResponseDTO> crear(@RequestBody NacimientoRequestDTO request) {
        return reproduccionClient.crearNacimiento(request);
    }

    /** Obtener todos los nacimientos */
    @GetMapping
    public Flux<NacimientoResponseDTO> obtenerTodos() {
        return reproduccionClient.obtenerTodosNacimientos();
    }

    /** Obtener nacimiento por ID */
    @GetMapping("/{id}")
    public Mono<NacimientoResponseDTO> obtenerPorId(@PathVariable UUID id) {
        return reproduccionClient.obtenerNacimientoPorId(id);
    }

    /** Obtener nacimientos por ID de madre */
    @GetMapping("/madre/{idMadre}")
    public Flux<NacimientoResponseDTO> obtenerPorIdMadre(@PathVariable UUID idMadre) {
        return reproduccionClient.obtenerNacimientosPorIdMadre(idMadre);
    }

    /** Actualizar nacimiento */
    @PutMapping("/{id}")
    public Mono<NacimientoResponseDTO> actualizar(@PathVariable UUID id,
                                                  @RequestBody NacimientoRequestDTO request) {
        return reproduccionClient.actualizarNacimiento(id, request);
    }

    /** Eliminar nacimiento */
    @DeleteMapping("/{id}")
    public Mono<Void> eliminar(@PathVariable UUID id) {
        return reproduccionClient.eliminarNacimiento(id);
    }
}