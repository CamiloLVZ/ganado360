package com.ganado.gateway.controller;

import com.ganado.gateway.dto.reproduccion.GenealogiaRequestDTO;
import com.ganado.gateway.dto.reproduccion.GenealogiaResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("/api/reproduccion/genealogias")
@RequiredArgsConstructor
public class GenealogiaController {

    private final ReproduccionClient reproduccionClient;

    /** Crear genealogía */
    @PostMapping
    public Mono<GenealogiaResponseDTO> crear(@RequestBody GenealogiaRequestDTO dto) {
        return reproduccionClient.crearGenealogia(dto);
    }

    /** Listar todas las genealogías */
    @GetMapping
    public Flux<GenealogiaResponseDTO> listar() {
        return reproduccionClient.listarTodasGenealogias();
    }

    /** Obtener genealogía por hijo (hijoId es la PK lógica) */
    @GetMapping("/{hijoId}")
    public Mono<GenealogiaResponseDTO> obtener(@PathVariable UUID hijoId) {
        return reproduccionClient.obtenerGenealogiaPorHijo(hijoId);
    }

    /** Obtener genealogías por madre */
    @GetMapping("/madre/{madreId}")
    public Flux<GenealogiaResponseDTO> obtenerPorMadre(@PathVariable UUID madreId) {
        return reproduccionClient.obtenerGenealogiasPorMadre(madreId);
    }

    /** Actualizar genealogía (busca por hijoId) */
    @PutMapping("/{hijoId}")
    public Mono<GenealogiaResponseDTO> actualizar(@PathVariable UUID hijoId, @RequestBody GenealogiaRequestDTO dto) {
        return reproduccionClient.actualizarGenealogia(hijoId, dto);
    }

    /** Eliminar genealogía por hijoId */
    @DeleteMapping("/{hijoId}")
    public Mono<Void> eliminar(@PathVariable UUID hijoId) {
        return reproduccionClient.eliminarGenealogia(hijoId);
    }
}