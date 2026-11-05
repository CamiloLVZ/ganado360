package com.ganado.reproduccion.controller;

import com.ganado.reproduccion.dto.MontaRequest;
import com.ganado.reproduccion.dto.MontaResponseDTO;
import com.ganado.reproduccion.model.Monta;
import com.ganado.reproduccion.service.MontaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/montas")
@RequiredArgsConstructor
public class MontaController {

    private final MontaService montaService;

    @PostMapping
    public ResponseEntity<MontaResponseDTO> crearMonta(@RequestBody MontaRequest request) {
        MontaResponseDTO response = montaService.registrarMonta(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MontaResponseDTO> obtenerPorId(@PathVariable UUID id) {
        MontaResponseDTO response = montaService.obtenerPorId(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<MontaResponseDTO>> obtenerTodos() {
        List<MontaResponseDTO> responseList = montaService.obtenerTodos();
        return ResponseEntity.ok(responseList);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MontaResponseDTO> actualizarMonta(@PathVariable UUID id, @RequestBody MontaRequest request) {
        MontaResponseDTO response = montaService.actualizarMonta(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarMonta(@PathVariable UUID id) {
        montaService.eliminarMonta(id);
        return ResponseEntity.noContent().build();
    }
}
