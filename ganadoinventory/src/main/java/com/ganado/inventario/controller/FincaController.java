package com.ganado.inventario.controller;

import com.ganado.inventario.dto.FincaRequestDTO;
import com.ganado.inventario.dto.FincaResponseDTO;
import com.ganado.inventario.service.FincaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/inventory/fincas")
@RequiredArgsConstructor
public class FincaController {

    private final FincaService fincaService;

    @PostMapping
    public ResponseEntity<FincaResponseDTO> crear(@RequestBody FincaRequestDTO dto) {
        System.out.println("[INVENTORY] Llegó POST /fincas");
        System.out.println("[INVENTORY] Body: " + dto);
        return ResponseEntity.ok(fincaService.crear(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FincaResponseDTO> obtener(@PathVariable UUID id) {
        return ResponseEntity.ok(fincaService.obtener(id));
    }

    @GetMapping
    public ResponseEntity<List<FincaResponseDTO>> listar() {
        return ResponseEntity.ok(fincaService.listar());
    }

    @PutMapping("/{id}")
    public ResponseEntity<FincaResponseDTO> actualizar(
            @PathVariable UUID id,
            @RequestBody FincaRequestDTO dto
    ) {
        return ResponseEntity.ok(fincaService.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable UUID id) {
        fincaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}