package com.ganado.gateway.controller;

import com.ganado.gateway.dto.sanidad.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("/api/sanidad/vacunas")
@RequiredArgsConstructor
public class VacunaController {

    private final SanidadClient sanidadClient;

    // ========================================
    // PRODUCTOS SANITARIOS / VACUNAS
    // ========================================

    /**
     * Crear vacuna/producto sanitario
     */
    @PostMapping
    public Mono<ProductoSanitarioResponseDTO> crear(@RequestBody ProductoSanitarioRequestDTO dto) {
        return sanidadClient.crearProductoSanitario(dto);
    }

    /**
     * Listar todas las vacunas/productos sanitarios
     */
    @GetMapping
    public Flux<ProductoSanitarioResponseDTO> listar() {
        return sanidadClient.listarProductosSanitarios();
    }

    /**
     * Obtener vacuna por ID
     */
    @GetMapping("/{id}")
    public Mono<ProductoSanitarioResponseDTO> obtener(@PathVariable UUID id) {
        return sanidadClient.obtenerProductoSanitarioPorId(id);
    }

    /**
     * Actualizar vacuna
     */
    @PutMapping("/{id}")
    public Mono<ProductoSanitarioResponseDTO> actualizar(@PathVariable UUID id,
                                                         @RequestBody ProductoSanitarioRequestDTO dto) {
        return sanidadClient.editarProductoSanitario(id, dto);
    }

    /**
     * Eliminar vacuna
     */
    @DeleteMapping("/{id}")
    public Mono<Void> eliminar(@PathVariable UUID id) {
        return sanidadClient.eliminarProductoSanitario(id);
    }

    /**
     * Obtener vacunas por especie
     */
    @GetMapping("/especie/{especie}")
    public Flux<ProductoSanitarioResponseDTO> obtenerPorEspecie(@PathVariable String especie) {
        return sanidadClient.obtenerProductosSanitariosPorEspecie(especie);
    }

    /**
     * Buscar vacuna por nombre (query param)
     */
    @GetMapping("/buscar")
    public Mono<ProductoSanitarioResponseDTO> buscarPorNombre(@RequestParam String nombre) {
        return sanidadClient.buscarProductoSanitarioPorNombre(nombre);
    }

    // ========================================
    // INCIDENCIAS DE VACUNACIÓN
    // ========================================

    /**
     * Crear incidencia de vacunación
     */
    @PostMapping("/incidencias")
    public Mono<IncidenciaVacunaResponseDTO> crearIncidencia(@RequestBody IncidenciaVacunaRequestDTO dto) {
        return sanidadClient.crearIncidenciaVacuna(dto);
    }

    /**
     * Listar todas las incidencias de vacunación
     */
    @GetMapping("/incidencias")
    public Flux<IncidenciaVacunaResponseDTO> listarIncidencias() {
        return sanidadClient.listarIncidenciasVacuna();
    }

    /**
     * Obtener incidencia de vacunación por ID
     */
    @GetMapping("/incidencias/{id}")
    public Mono<IncidenciaVacunaResponseDTO> obtenerIncidencia(@PathVariable UUID id) {
        return sanidadClient.obtenerIncidenciaVacunaPorId(id);
    }

    /**
     * Obtener incidencias de vacunación por animal
     */
    @GetMapping("/incidencias/animal/{idAnimal}")
    public Flux<IncidenciaVacunaResponseDTO> obtenerIncidenciasPorAnimal(@PathVariable UUID idAnimal) {
        return sanidadClient.obtenerIncidenciasVacunaPorAnimal(idAnimal);
    }

    /**
     * Obtener incidencias de vacunación por producto/vacuna
     */
    @GetMapping("/incidencias/producto/{productoId}")
    public Flux<IncidenciaVacunaResponseDTO> obtenerIncidenciasPorProducto(@PathVariable UUID productoId) {
        return sanidadClient.obtenerIncidenciasVacunaPorProducto(productoId);
    }

    /**
     * Obtener incidencias de vacunación por estado
     */
    @GetMapping("/incidencias/estado/{estado}")
    public Flux<IncidenciaVacunaResponseDTO> obtenerIncidenciasPorEstado(@PathVariable String estado) {
        return sanidadClient.obtenerIncidenciasVacunaPorEstado(estado);
    }

    /**
     * Actualizar incidencia de vacunación
     */
    @PutMapping("/incidencias/{id}")
    public Mono<IncidenciaVacunaResponseDTO> actualizarIncidencia(@PathVariable UUID id,
                                                                  @RequestBody IncidenciaVacunaRequestDTO dto) {
        return sanidadClient.actualizarIncidenciaVacuna(id, dto);
    }

    /**
     * Anular incidencia de vacunación (DELETE lógico)
     */
    @DeleteMapping("/incidencias/{id}")
    public Mono<IncidenciaVacunaResponseDTO> anularIncidencia(@PathVariable UUID id) {
        return sanidadClient.anularIncidenciaVacuna(id);
    }
}