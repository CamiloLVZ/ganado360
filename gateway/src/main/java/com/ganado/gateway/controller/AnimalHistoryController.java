package com.ganado.gateway.controller;

import com.ganado.gateway.dto.inventory.AnimalHistoryRequestDTO;
import com.ganado.gateway.dto.inventory.AnimalHistoryResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("/api/inventory/animales/historias")
@RequiredArgsConstructor
public class AnimalHistoryController {

    private final InventoryClient inventoryClient;

    /** Crear un registro de historial */
    @PostMapping
    public Mono<AnimalHistoryResponseDTO> crear(@RequestBody AnimalHistoryRequestDTO dto) {
        return inventoryClient.crearAnimalHistory(dto);
    }

    /** Listar todos los historiales */
    @GetMapping
    public Flux<AnimalHistoryResponseDTO> listar() {
        return inventoryClient.listarAnimalHistory();
    }

    /** Obtener historial por ID */
    @GetMapping("/{id}")
    public Mono<AnimalHistoryResponseDTO> obtener(@PathVariable UUID id) {
        return inventoryClient.obtenerAnimalHistoryPorId(id);
    }

    /** Listar historial por animal */
    @GetMapping("/animal/{animalId}")
    public Flux<AnimalHistoryResponseDTO> listarPorAnimal(@PathVariable UUID animalId) {
        return inventoryClient.listarAnimalHistoryPorAnimal(animalId);
    }

    /** Actualizar historial */
    @PutMapping("/{id}")
    public Mono<AnimalHistoryResponseDTO> actualizar(@PathVariable UUID id, @RequestBody AnimalHistoryRequestDTO dto) {
        return inventoryClient.actualizarAnimalHistory(id, dto);
    }

    /** Eliminar historial */
    @DeleteMapping("/{id}")
    public Mono<Void> eliminar(@PathVariable UUID id) {
        return inventoryClient.eliminarAnimalHistory(id);
    }

    @GetMapping("/animal/{animalId}/export/pdf")
    public Mono<byte[]> exportarHistorialAnimal(@PathVariable UUID animalId){
        return inventoryClient.exportarHistorialAnimal(animalId);
    }
}