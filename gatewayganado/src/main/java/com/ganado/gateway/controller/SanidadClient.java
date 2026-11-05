package com.ganado.gateway.controller;

import com.ganado.gateway.dto.sanidad.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
public class SanidadClient {

    private final WebClient webClient;

    public SanidadClient(WebClient.Builder builder, @Value("${sanidad.service.url}") String baseUrl) {
        this.webClient = builder
                .baseUrl(baseUrl) // microservicio de sanidad
                .build();
    }

    // -----------------------------
    //  ENFERMEDADES
    // -----------------------------

    /**
     * Listar todas las enfermedades
     */
    public Flux<EnfermedadResponseDTO> listarEnfermedades() {
        return webClient.get()
                .uri("/api/enfermedades/all")
                .retrieve()
                .bodyToFlux(EnfermedadResponseDTO.class);
    }

    /**
     * Crear una enfermedad
     */
    public Mono<EnfermedadResponseDTO> crearEnfermedad(EnfermedadRequestDTO dto) {
        return webClient.post()
                .uri("/api/enfermedades")
                .bodyValue(dto)
                .retrieve()
                .bodyToMono(EnfermedadResponseDTO.class);
    }

    /**
     * Obtener enfermedad por ID
     */
    public Mono<EnfermedadResponseDTO> obtenerEnfermedadPorId(UUID id) {
        return webClient.get()
                .uri("/api/enfermedades/{id}", id)
                .retrieve()
                .bodyToMono(EnfermedadResponseDTO.class);
    }

    /**
     * Editar enfermedad
     */
    public Mono<EnfermedadResponseDTO> editarEnfermedad(UUID id, EnfermedadRequestDTO dto) {
        return webClient.put()
                .uri("/api/enfermedades/{id}", id)
                .bodyValue(dto)
                .retrieve()
                .bodyToMono(EnfermedadResponseDTO.class);
    }

    /**
     * Eliminar enfermedad
     */
    public Mono<Void> eliminarEnfermedad(UUID id) {
        return webClient.delete()
                .uri("/api/enfermedades/{id}", id)
                .retrieve()
                .bodyToMono(Void.class);
    }

    /**
     * Buscar enfermedad por nombre (query param)
     */
    public Mono<EnfermedadResponseDTO> buscarEnfermedadPorNombre(String nombre) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/enfermedades/buscar")
                        .queryParam("nombre", nombre)
                        .build())
                .retrieve()
                .bodyToMono(EnfermedadResponseDTO.class);
    }

    // -----------------------------
    //  INCIDENCIAS DE ENFERMEDAD
    // -----------------------------

    /**
     * Listar todas las incidencias de enfermedad
     */
    public Flux<IncidenciaEnfermedadResponseDTO> listarIncidenciasEnfermedad() {
        return webClient.get()
                .uri("/api/incidencias-enfermedad/all")
                .retrieve()
                .bodyToFlux(IncidenciaEnfermedadResponseDTO.class);
    }

    /**
     * Crear una incidencia de enfermedad
     */
    public Mono<IncidenciaEnfermedadResponseDTO> crearIncidenciaEnfermedad(IncidenciaEnfermedadRequestDTO dto) {
        return webClient.post()
                .uri("/api/incidencias-enfermedad")
                .bodyValue(dto)
                .retrieve()
                .bodyToMono(IncidenciaEnfermedadResponseDTO.class);
    }

    /**
     * Obtener incidencia de enfermedad por ID
     */
    public Mono<IncidenciaEnfermedadResponseDTO> obtenerIncidenciaEnfermedadPorId(UUID id) {
        return webClient.get()
                .uri("/api/incidencias-enfermedad/{id}", id)
                .retrieve()
                .bodyToMono(IncidenciaEnfermedadResponseDTO.class);
    }

    /**
     * Obtener incidencias de enfermedad por animal
     */
    public Flux<IncidenciaEnfermedadResponseDTO> obtenerIncidenciasEnfermedadPorAnimal(UUID idAnimal) {
        return webClient.get()
                .uri("/api/incidencias-enfermedad/animal/{idAnimal}", idAnimal)
                .retrieve()
                .bodyToFlux(IncidenciaEnfermedadResponseDTO.class);
    }

    /**
     * Obtener incidencias de enfermedad por enfermedad
     */
    public Flux<IncidenciaEnfermedadResponseDTO> obtenerIncidenciasEnfermedadPorEnfermedad(UUID enfermedadId) {
        return webClient.get()
                .uri("/api/incidencias-enfermedad/enfermedad/{enfermedadId}", enfermedadId)
                .retrieve()
                .bodyToFlux(IncidenciaEnfermedadResponseDTO.class);
    }

    /**
     * Actualizar incidencia de enfermedad
     */
    public Mono<IncidenciaEnfermedadResponseDTO> actualizarIncidenciaEnfermedad(UUID id, IncidenciaEnfermedadRequestDTO dto) {
        return webClient.put()
                .uri("/api/incidencias-enfermedad/{id}", id)
                .bodyValue(dto)
                .retrieve()
                .bodyToMono(IncidenciaEnfermedadResponseDTO.class);
    }

    /**
     * Eliminar incidencia de enfermedad
     */
    public Mono<Void> eliminarIncidenciaEnfermedad(UUID id) {
        return webClient.delete()
                .uri("/api/incidencias-enfermedad/{id}", id)
                .retrieve()
                .bodyToMono(Void.class);
    }

    // -----------------------------
    //  INCIDENCIAS DE TRATAMIENTO
    // -----------------------------

    /**
     * Listar todas las incidencias de tratamiento
     */
    public Flux<IncidenciaTratamientoResponseDTO> listarIncidenciasTratamiento() {
        return webClient.get()
                .uri("/api/incidencias-tratamientos/all")
                .retrieve()
                .bodyToFlux(IncidenciaTratamientoResponseDTO.class);
    }

    /**
     * Crear una incidencia de tratamiento
     */
    public Mono<IncidenciaTratamientoResponseDTO> crearIncidenciaTratamiento(IncidenciaTratamientoRequestDTO dto) {
        System.out.println(dto.getIncidenciaEnfermedadId());
        return webClient.post()
                .uri("/api/incidencias-tratamientos")
                .bodyValue(dto)
                .retrieve()
                .bodyToMono(IncidenciaTratamientoResponseDTO.class);
    }

    /**
     * Obtener incidencia de tratamiento por ID
     */
    public Mono<IncidenciaTratamientoResponseDTO> obtenerIncidenciaTratamientoPorId(UUID id) {
        return webClient.get()
                .uri("/api/incidencias-tratamientos/{id}", id)
                .retrieve()
                .bodyToMono(IncidenciaTratamientoResponseDTO.class);
    }

    /**
     * Obtener incidencias de tratamiento por animal
     */
    public Flux<IncidenciaTratamientoResponseDTO> obtenerIncidenciasTratamientoPorAnimal(UUID idAnimal) {
        return webClient.get()
                .uri("/api/incidencias-tratamientos/animal/{idAnimal}", idAnimal)
                .retrieve()
                .bodyToFlux(IncidenciaTratamientoResponseDTO.class);
    }

    /**
     * Obtener incidencias de tratamiento por tratamiento
     */
    public Flux<IncidenciaTratamientoResponseDTO> obtenerIncidenciasTratamientoPorTratamiento(UUID tratamientoId) {
        return webClient.get()
                .uri("/api/incidencias-tratamientos/tratamiento/{tratamientoId}", tratamientoId)
                .retrieve()
                .bodyToFlux(IncidenciaTratamientoResponseDTO.class);
    }

    /**
     * Obtener incidencias de tratamiento por estado
     */
    public Flux<IncidenciaTratamientoResponseDTO> obtenerIncidenciasTratamientoPorEstado(String estado) {
        return webClient.get()
                .uri("/api/incidencias-tratamientos/estado/{estado}", estado)
                .retrieve()
                .bodyToFlux(IncidenciaTratamientoResponseDTO.class);
    }

    /**
     * Actualizar incidencia de tratamiento
     */
    public Mono<IncidenciaTratamientoResponseDTO> actualizarIncidenciaTratamiento(UUID id, IncidenciaTratamientoRequestDTO dto) {
        return webClient.put()
                .uri("/api/incidencias-tratamientos/{id}", id)
                .bodyValue(dto)
                .retrieve()
                .bodyToMono(IncidenciaTratamientoResponseDTO.class);
    }

    /**
     * Anular incidencia de tratamiento (DELETE lógico)
     */
    public Mono<IncidenciaTratamientoResponseDTO> anularIncidenciaTratamiento(UUID id) {
        return webClient.delete()
                .uri("/api/incidencias-tratamientos/{id}", id)
                .retrieve()
                .bodyToMono(IncidenciaTratamientoResponseDTO.class);
    }

    // -----------------------------
    //  INCIDENCIAS DE VACUNA
    // -----------------------------

    /**
     * Listar todas las incidencias de vacuna
     */
    public Flux<IncidenciaVacunaResponseDTO> listarIncidenciasVacuna() {
        return webClient.get()
                .uri("/api/incidencias-vacunas/all")
                .retrieve()
                .bodyToFlux(IncidenciaVacunaResponseDTO.class);
    }

    /**
     * Crear una incidencia de vacuna
     */
    public Mono<IncidenciaVacunaResponseDTO> crearIncidenciaVacuna(IncidenciaVacunaRequestDTO dto) {
        return webClient.post()
                .uri("/api/incidencias-vacunas")
                .bodyValue(dto)
                .retrieve()
                .bodyToMono(IncidenciaVacunaResponseDTO.class);
    }

    /**
     * Obtener incidencia de vacuna por ID
     */
    public Mono<IncidenciaVacunaResponseDTO> obtenerIncidenciaVacunaPorId(UUID id) {
        return webClient.get()
                .uri("/api/incidencias-vacunas/{id}", id)
                .retrieve()
                .bodyToMono(IncidenciaVacunaResponseDTO.class);
    }

    /**
     * Obtener incidencias de vacuna por animal
     */
    public Flux<IncidenciaVacunaResponseDTO> obtenerIncidenciasVacunaPorAnimal(UUID idAnimal) {
        return webClient.get()
                .uri("/api/incidencias-vacunas/animal/{idAnimal}", idAnimal)
                .retrieve()
                .bodyToFlux(IncidenciaVacunaResponseDTO.class);
    }

    /**
     * Obtener incidencias de vacuna por producto
     */
    public Flux<IncidenciaVacunaResponseDTO> obtenerIncidenciasVacunaPorProducto(UUID productoId) {
        return webClient.get()
                .uri("/api/incidencias-vacunas/producto/{productoId}", productoId)
                .retrieve()
                .bodyToFlux(IncidenciaVacunaResponseDTO.class);
    }

    /**
     * Obtener incidencias de vacuna por estado
     */
    public Flux<IncidenciaVacunaResponseDTO> obtenerIncidenciasVacunaPorEstado(String estado) {
        return webClient.get()
                .uri("/api/incidencias-vacunas/estado/{estado}", estado)
                .retrieve()
                .bodyToFlux(IncidenciaVacunaResponseDTO.class);
    }

    /**
     * Actualizar incidencia de vacuna
     */
    public Mono<IncidenciaVacunaResponseDTO> actualizarIncidenciaVacuna(UUID id, IncidenciaVacunaRequestDTO dto) {
        return webClient.put()
                .uri("/api/incidencias-vacunas/{id}", id)
                .bodyValue(dto)
                .retrieve()
                .bodyToMono(IncidenciaVacunaResponseDTO.class);
    }

    /**
     * Anular incidencia de vacuna (DELETE lógico)
     */
    public Mono<IncidenciaVacunaResponseDTO> anularIncidenciaVacuna(UUID id) {
        return webClient.delete()
                .uri("/api/incidencias-vacunas/{id}", id)
                .retrieve()
                .bodyToMono(IncidenciaVacunaResponseDTO.class);
    }

    /**
     * Listar todos los productos sanitarios
     */
    public Flux<ProductoSanitarioResponseDTO> listarProductosSanitarios() {
        return webClient.get()
                .uri("/api/productos-sanitarios/all")
                .retrieve()
                .bodyToFlux(ProductoSanitarioResponseDTO.class);
    }

    /**
     * Crear un producto sanitario
     */
    public Mono<ProductoSanitarioResponseDTO> crearProductoSanitario(ProductoSanitarioRequestDTO dto) {
        return webClient.post()
                .uri("/api/productos-sanitarios")
                .bodyValue(dto)
                .retrieve()
                .bodyToMono(ProductoSanitarioResponseDTO.class);
    }

    /**
     * Obtener producto sanitario por ID
     */
    public Mono<ProductoSanitarioResponseDTO> obtenerProductoSanitarioPorId(UUID id) {
        return webClient.get()
                .uri("/api/productos-sanitarios/{id}", id)
                .retrieve()
                .bodyToMono(ProductoSanitarioResponseDTO.class);
    }

    /**
     * Editar producto sanitario
     */
    public Mono<ProductoSanitarioResponseDTO> editarProductoSanitario(UUID id, ProductoSanitarioRequestDTO dto) {
        return webClient.put()
                .uri("/api/productos-sanitarios/{id}", id)
                .bodyValue(dto)
                .retrieve()
                .bodyToMono(ProductoSanitarioResponseDTO.class);
    }

    /**
     * Eliminar producto sanitario
     */
    public Mono<Void> eliminarProductoSanitario(UUID id) {
        return webClient.delete()
                .uri("/api/productos-sanitarios/{id}", id)
                .retrieve()
                .bodyToMono(Void.class);
    }

    /**
     * Obtener productos sanitarios por especie
     */
    public Flux<ProductoSanitarioResponseDTO> obtenerProductosSanitariosPorEspecie(String especie) {
        return webClient.get()
                .uri("/api/productos-sanitarios/especie/{especie}", especie)
                .retrieve()
                .bodyToFlux(ProductoSanitarioResponseDTO.class);
    }

    /**
     * Buscar producto sanitario por nombre (query param)
     */
    public Mono<ProductoSanitarioResponseDTO> buscarProductoSanitarioPorNombre(String nombre) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/productos-sanitarios/buscar")
                        .queryParam("nombre", nombre)
                        .build())
                .retrieve()
                .bodyToMono(ProductoSanitarioResponseDTO.class);
    }

    // -----------------------------
    //  REFUERZOS
    // -----------------------------

    /**
     * Listar todos los refuerzos
     */
    public Flux<RefuerzoResponseDTO> listarRefuerzos() {
        return webClient.get()
                .uri("/api/refuerzos/all")
                .retrieve()
                .bodyToFlux(RefuerzoResponseDTO.class);
    }

    /**
     * Crear un refuerzo
     */
    public Mono<RefuerzoResponseDTO> crearRefuerzo(RefuerzoRequestDTO dto) {
        return webClient.post()
                .uri("/api/refuerzos")
                .bodyValue(dto)
                .retrieve()
                .bodyToMono(RefuerzoResponseDTO.class);
    }

    /**
     * Obtener refuerzo por ID
     */
    public Mono<RefuerzoResponseDTO> obtenerRefuerzoPorId(UUID id) {
        return webClient.get()
                .uri("/api/refuerzos/{id}", id)
                .retrieve()
                .bodyToMono(RefuerzoResponseDTO.class);
    }

    /**
     * Editar refuerzo
     */
    public Mono<RefuerzoResponseDTO> editarRefuerzo(UUID id, RefuerzoRequestDTO dto) {
        return webClient.put()
                .uri("/api/refuerzos/{id}", id)
                .bodyValue(dto)
                .retrieve()
                .bodyToMono(RefuerzoResponseDTO.class);
    }

    /**
     * Eliminar refuerzo
     */
    public Mono<Void> eliminarRefuerzo(UUID id) {
        return webClient.delete()
                .uri("/api/refuerzos/{id}", id)
                .retrieve()
                .bodyToMono(Void.class);
    }

    /**
     * Obtener refuerzos por vacuna
     */
    public Flux<RefuerzoResponseDTO> obtenerRefuerzosPorVacuna(UUID vacunaId) {
        return webClient.get()
                .uri("/api/refuerzos/vacuna/{vacunaId}", vacunaId)
                .retrieve()
                .bodyToFlux(RefuerzoResponseDTO.class);
    }

    /**
     * Listar todos los tratamientos
     */
    public Flux<TratamientoResponseDTO> listarTratamientos() {
        return webClient.get()
                .uri("/api/tratamientos/all")
                .retrieve()
                .bodyToFlux(TratamientoResponseDTO.class);
    }

    /**
     * Crear un tratamiento
     */
    public Mono<TratamientoResponseDTO> crearTratamiento(TratamientoRequestDTO dto) {
        System.out.println("2");
        return webClient.post()
                .uri("/api/tratamientos")
                .bodyValue(dto)
                .retrieve()
                .bodyToMono(TratamientoResponseDTO.class);
    }

    /**
     * Obtener tratamiento por ID
     */
    public Mono<TratamientoResponseDTO> obtenerTratamientoPorId(UUID id) {
        return webClient.get()
                .uri("/api/tratamientos/{id}", id)
                .retrieve()
                .bodyToMono(TratamientoResponseDTO.class);
    }

    /**
     * Editar tratamiento
     */
    public Mono<TratamientoResponseDTO> editarTratamiento(UUID id, TratamientoRequestDTO dto) {
        return webClient.put()
                .uri("/api/tratamientos/{id}", id)
                .bodyValue(dto)
                .retrieve()
                .bodyToMono(TratamientoResponseDTO.class);
    }

    /**
     * Eliminar tratamiento
     */
    public Mono<Void> eliminarTratamiento(UUID id) {
        return webClient.delete()
                .uri("/api/tratamientos/{id}", id)
                .retrieve()
                .bodyToMono(Void.class);
    }

    /**
     * Buscar tratamiento por nombre (query param)
     */
    public Mono<TratamientoResponseDTO> buscarTratamientoPorNombre(String nombre) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/tratamientos/buscar")
                        .queryParam("nombre", nombre)
                        .build())
                .retrieve()
                .bodyToMono(TratamientoResponseDTO.class);
    }

    /**
     * Buscar tratamientos por descripción (query param)
     */
    public Flux<TratamientoResponseDTO> buscarTratamientosPorDescripcion(String descripcion) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/tratamientos/buscar/descripcion")
                        .queryParam("q", descripcion)
                        .build())
                .retrieve()
                .bodyToFlux(TratamientoResponseDTO.class);
    }
}