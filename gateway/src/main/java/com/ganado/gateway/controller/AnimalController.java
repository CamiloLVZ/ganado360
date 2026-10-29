package com.ganado.gateway.controller;

import com.ganado.gateway.dto.inventory.AnimalRequestDTO;
import com.ganado.gateway.dto.inventory.AnimalResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/inventory/animales")
@RequiredArgsConstructor
public class AnimalController {

    private final InventoryClient inventoryClient;

    /** Crear animal */
    @PostMapping
    public Mono<AnimalResponseDTO> crear(@RequestBody AnimalRequestDTO dto) {
        return inventoryClient.crearAnimal(dto);
    }

    /** Listar todos los animales */
    @GetMapping
    public Flux<AnimalResponseDTO> listar() {
        return inventoryClient.listarAnimales();
    }

    /** Obtener animal por ID */
    @GetMapping("/{id}")
    public Mono<AnimalResponseDTO> obtener(@PathVariable UUID id) {
        return inventoryClient.obtenerAnimalPorId(id);
    }

    /** Actualizar animal */
    @PutMapping("/{id}")
    public Mono<AnimalResponseDTO> actualizar(@PathVariable UUID id, @RequestBody AnimalRequestDTO dto) {
        return inventoryClient.actualizarAnimal(id, dto);
    }

    /** Dar de baja */
    @DeleteMapping("/{id}")
    public Mono<Void> eliminar(@PathVariable UUID id) {
        return inventoryClient.eliminarAnimal(id);
    }

    @GetMapping("/especie/{especie}")
    public Flux<AnimalResponseDTO> obtenerPorEspecie(@PathVariable String especie){
        return inventoryClient.obtenerPorEspecie(especie);
    }
}