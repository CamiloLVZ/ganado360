package com.ganado.gateway.controller;

import com.ganado.gateway.dto.sanidad.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("/api/sanidad/tratamientos")
@RequiredArgsConstructor
public class TratamientoController {

    private final SanidadClient sanidadClient;

    // ========================================
    // TRATAMIENTOS
    // ========================================

    /**
     * Crear tratamiento
     */
    @PostMapping
    public Mono<TratamientoResponseDTO> crear(@RequestBody TratamientoRequestDTO dto) {
        System.out.println("jiaja");
        return sanidadClient.crearTratamiento(dto);
    }

    /**
     * Listar todos los tratamientos
     */
    @GetMapping
    public Flux<TratamientoResponseDTO> listar() {
        return sanidadClient.listarTratamientos();
    }

    /**
     * Obtener tratamiento por ID
     */
    @GetMapping("/{id}")
    public Mono<TratamientoResponseDTO> obtener(@PathVariable UUID id) {
        return sanidadClient.obtenerTratamientoPorId(id);
    }

    /**
     * Actualizar tratamiento
     */
    @PutMapping("/{id}")
    public Mono<TratamientoResponseDTO> actualizar(@PathVariable UUID id,
                                                   @RequestBody TratamientoRequestDTO dto) {
        return sanidadClient.editarTratamiento(id, dto);
    }

    /**
     * Eliminar tratamiento
     */
    @DeleteMapping("/{id}")
    public Mono<Void> eliminar(@PathVariable UUID id) {
        return sanidadClient.eliminarTratamiento(id);
    }

    /**
     * Buscar tratamientos por descripción (query param)
     */
    @GetMapping("/buscar/descripcion")
    public Flux<TratamientoResponseDTO> buscarPorDescripcion(@RequestParam String q) {
        return sanidadClient.buscarTratamientosPorDescripcion(q);
    }

    // ========================================
    // INCIDENCIAS DE TRATAMIENTO
    // ========================================

    /**
     * Crear incidencia de tratamiento
     */
    @PostMapping("/incidencias")
    public Mono<IncidenciaTratamientoResponseDTO> crearIncidencia(@RequestBody IncidenciaTratamientoRequestDTO dto) {

        return sanidadClient.crearIncidenciaTratamiento(dto);
    }

    /**
     * Listar todas las incidencias de tratamiento
     */
    @GetMapping("/incidencias")
    public Flux<IncidenciaTratamientoResponseDTO> listarIncidencias() {
        return sanidadClient.listarIncidenciasTratamiento();
    }

    /**
     * Obtener incidencia de tratamiento por ID
     */
    @GetMapping("/incidencias/{id}")
    public Mono<IncidenciaTratamientoResponseDTO> obtenerIncidencia(@PathVariable UUID id) {
        return sanidadClient.obtenerIncidenciaTratamientoPorId(id);
    }

    /**
     * Obtener incidencias de tratamiento por animal
     */
    @GetMapping("/incidencias/animal/{idAnimal}")
    public Flux<IncidenciaTratamientoResponseDTO> obtenerIncidenciasPorAnimal(@PathVariable UUID idAnimal) {
        return sanidadClient.obtenerIncidenciasTratamientoPorAnimal(idAnimal);
    }

    /**
     * Obtener incidencias de tratamiento por tratamiento
     */
    @GetMapping("/incidencias/tratamiento/{tratamientoId}")
    public Flux<IncidenciaTratamientoResponseDTO> obtenerIncidenciasPorTratamiento(@PathVariable UUID tratamientoId) {
        return sanidadClient.obtenerIncidenciasTratamientoPorTratamiento(tratamientoId);
    }

    /**
     * Obtener incidencias de tratamiento por estado
     */
    @GetMapping("/incidencias/estado/{estado}")
    public Flux<IncidenciaTratamientoResponseDTO> obtenerIncidenciasPorEstado(@PathVariable String estado) {
        return sanidadClient.obtenerIncidenciasTratamientoPorEstado(estado);
    }

    /**
     * Actualizar incidencia de tratamiento
     */
    @PutMapping("/incidencias/{id}")
    public Mono<IncidenciaTratamientoResponseDTO> actualizarIncidencia(@PathVariable UUID id,
                                                                       @RequestBody IncidenciaTratamientoRequestDTO dto) {
        return sanidadClient.actualizarIncidenciaTratamiento(id, dto);
    }

    /**
     * Anular incidencia de tratamiento (DELETE lógico)
     */
    @DeleteMapping("/incidencias/{id}")
    public Mono<IncidenciaTratamientoResponseDTO> anularIncidencia(@PathVariable UUID id) {
        return sanidadClient.anularIncidenciaTratamiento(id);
    }
}