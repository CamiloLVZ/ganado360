package com.ganado.inventario.controller;


import com.ganado.inventario.dto.AnimalHistoryRequestDTO;
import com.ganado.inventario.dto.AnimalHistoryResponseDTO;
import com.ganado.inventario.service.AnimalHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/inventory/animales/historias")
@RequiredArgsConstructor
public class AnimalHistoryController {

    private final AnimalHistoryService historyService;

    @PostMapping
    public ResponseEntity<AnimalHistoryResponseDTO> crear(@RequestBody AnimalHistoryRequestDTO request) {
        return ResponseEntity.ok(historyService.crear(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AnimalHistoryResponseDTO> obtenerPorId(@PathVariable UUID id) {
        return ResponseEntity.ok(historyService.obtenerPorId(id));
    }

    @GetMapping
    public ResponseEntity<List<AnimalHistoryResponseDTO>> listarTodos() {
        return ResponseEntity.ok(historyService.listarTodos());
    }

    @GetMapping("/animal/{animalId}")
    public ResponseEntity<List<AnimalHistoryResponseDTO>> listarPorAnimal(@PathVariable UUID animalId) {
        return ResponseEntity.ok(historyService.listarPorAnimal(animalId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AnimalHistoryResponseDTO> actualizar(
            @PathVariable UUID id,
            @RequestBody AnimalHistoryRequestDTO request
    ) {
        return ResponseEntity.ok(historyService.actualizar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable UUID id) {
        historyService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/animal/{animalId}/export/pdf")
    public ResponseEntity<byte[]> exportarHistorialAnimal(
            @PathVariable UUID animalId
    ) {
        byte[] pdf = historyService.exportarHistorialAnimalPdf(animalId);

        return ResponseEntity.ok()
                .header("Content-Type", "application/pdf")
                .header("Content-Disposition", "attachment; filename=historial_" + animalId + ".pdf")
                .body(pdf);
    }
}