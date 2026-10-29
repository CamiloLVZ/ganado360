package com.ganado.inventario.controller;
import com.ganado.inventario.dto.AnimalRequestDTO;
import com.ganado.inventario.dto.AnimalResponseDTO;
import com.ganado.inventario.service.AnimalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/inventory/animales")
@RequiredArgsConstructor
public class AnimalController {

    private final AnimalService animalService;

    @PostMapping
    public ResponseEntity<AnimalResponseDTO> crear(@RequestBody AnimalRequestDTO request) {
        return ResponseEntity.ok(animalService.crear(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AnimalResponseDTO> obtenerPorId(@PathVariable UUID id) {
        return ResponseEntity.ok(animalService.obtenerPorId(id));
    }

    @GetMapping
    public ResponseEntity<List<AnimalResponseDTO>> listar() {
        return ResponseEntity.ok(animalService.listarTodos());
    }

    @PutMapping("/{id}")
    public ResponseEntity<AnimalResponseDTO> actualizar(
            @PathVariable UUID id,
            @RequestBody AnimalRequestDTO request
    ) {
        return ResponseEntity.ok(animalService.actualizar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable UUID id) {
        animalService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/especie/{especie}")
    public ResponseEntity<List<AnimalResponseDTO>> obtenerPorEspecie(@PathVariable String especie) {
        List<AnimalResponseDTO> resultados = animalService.obtenerPorEspecie(especie);
        return ResponseEntity.ok(resultados);
    }
}