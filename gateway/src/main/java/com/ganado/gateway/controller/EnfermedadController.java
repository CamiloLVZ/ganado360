package com.ganado.gateway.controller;


import com.ganado.gateway.dto.sanidad.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("/api/sanidad/enfermedades")
@RequiredArgsConstructor
public class EnfermedadController {

    private final SanidadClient sanidadClient;

    // ========================================
    // ENFERMEDADES
    // ========================================

    /**
     * Crear enfermedad
     */
    @PostMapping
    public Mono<EnfermedadResponseDTO> crear(@RequestBody EnfermedadRequestDTO dto) {
        return sanidadClient.crearEnfermedad(dto);
    }

    /**
     * Listar todas las enfermedades
     */
    @GetMapping
    public Flux<EnfermedadResponseDTO> listar() {
        return sanidadClient.listarEnfermedades();
    }

    /**
     * Obtener enfermedad por ID
     */
    @GetMapping("/{id}")
    public Mono<EnfermedadResponseDTO> obtener(@PathVariable UUID id) {
        return sanidadClient.obtenerEnfermedadPorId(id);
    }

    /**
     * Actualizar enfermedad
     */
    @PutMapping("/{id}")
    public Mono<EnfermedadResponseDTO> actualizar(@PathVariable UUID id,
                                                  @RequestBody EnfermedadRequestDTO dto) {
        return sanidadClient.editarEnfermedad(id, dto);
    }

    /**
     * Eliminar enfermedad
     */
    @DeleteMapping("/{id}")
    public Mono<Void> eliminar(@PathVariable UUID id) {
        return sanidadClient.eliminarEnfermedad(id);
    }

    /**
     * Buscar enfermedad por nombre (query param)
     */
    @GetMapping("/buscar")
    public Mono<EnfermedadResponseDTO> buscarPorNombre(@RequestParam String nombre) {
        return sanidadClient.buscarEnfermedadPorNombre(nombre);
    }

    // ========================================
    // INCIDENCIAS DE ENFERMEDAD
    // ========================================

    /**
     * Crear incidencia de enfermedad
     */
    @PostMapping("/incidencias")
    public Mono<IncidenciaEnfermedadResponseDTO> crearIncidencia(@RequestBody IncidenciaEnfermedadRequestDTO dto) {
        return sanidadClient.crearIncidenciaEnfermedad(dto);
    }

    /**
     * Listar todas las incidencias de enfermedad
     */
    @GetMapping("/incidencias")
    public Flux<IncidenciaEnfermedadResponseDTO> listarIncidencias() {
        return sanidadClient.listarIncidenciasEnfermedad();
    }

    /**
     * Obtener incidencia de enfermedad por ID
     */
    @GetMapping("/incidencias/{id}")
    public Mono<IncidenciaEnfermedadResponseDTO> obtenerIncidencia(@PathVariable UUID id) {
        return sanidadClient.obtenerIncidenciaEnfermedadPorId(id);
    }

    /**
     * Obtener incidencias de enfermedad por animal
     */
    @GetMapping("/incidencias/animal/{idAnimal}")
    public Flux<IncidenciaEnfermedadResponseDTO> obtenerIncidenciasPorAnimal(@PathVariable UUID idAnimal) {
        return sanidadClient.obtenerIncidenciasEnfermedadPorAnimal(idAnimal);
    }

    /**
     * Obtener incidencias de enfermedad por enfermedad
     */
    @GetMapping("/incidencias/enfermedad/{enfermedadId}")
    public Flux<IncidenciaEnfermedadResponseDTO> obtenerIncidenciasPorEnfermedad(@PathVariable UUID enfermedadId) {
        return sanidadClient.obtenerIncidenciasEnfermedadPorEnfermedad(enfermedadId);
    }

    /**
     * Actualizar incidencia de enfermedad
     */
    @PutMapping("/incidencias/{id}")
    public Mono<IncidenciaEnfermedadResponseDTO> actualizarIncidencia(@PathVariable UUID id,
                                                                      @RequestBody IncidenciaEnfermedadRequestDTO dto) {
        return sanidadClient.actualizarIncidenciaEnfermedad(id, dto);
    }

    /**
     * Eliminar incidencia de enfermedad
     */
    @DeleteMapping("/incidencias/{id}")
    public Mono<Void> eliminarIncidencia(@PathVariable UUID id) {
        return sanidadClient.eliminarIncidenciaEnfermedad(id);
    }
}
