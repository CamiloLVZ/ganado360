package com.ganado.reproduccion.controller;

import com.ganado.reproduccion.dto.DiagnosticoGestacionRequest;
import com.ganado.reproduccion.dto.DiagnosticoGestacionResponseDTO;
import com.ganado.reproduccion.model.DiagnosticoGestacion;
import com.ganado.reproduccion.service.DiagnosticoGestacionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/diagnosticos")
@RequiredArgsConstructor
public class DiagnosticoGestacionController {

    private final DiagnosticoGestacionService diagnosticoGestacionService;

    @PostMapping
    public ResponseEntity<DiagnosticoGestacionResponseDTO> registrar(@RequestBody DiagnosticoGestacionRequest request) {
        DiagnosticoGestacionResponseDTO diagnostico = diagnosticoGestacionService.registrarDiagnostico(request);
        return ResponseEntity.ok(diagnostico);
    }

    @GetMapping
    public ResponseEntity<List<DiagnosticoGestacionResponseDTO>> listarTodos() {
        return ResponseEntity.ok(diagnosticoGestacionService.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DiagnosticoGestacionResponseDTO> obtenerPorId(@PathVariable UUID id) {
        return ResponseEntity.ok(diagnosticoGestacionService.obtenerPorId(id));
    }

    @GetMapping("/monta/{idMonta}")
    public ResponseEntity<List<DiagnosticoGestacionResponseDTO>> obtenerPorMonta(@PathVariable UUID idMonta) {
        return ResponseEntity.ok(diagnosticoGestacionService.obtenerPorMonta(idMonta));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DiagnosticoGestacionResponseDTO> actualizar(@PathVariable UUID id,
                                                                      @RequestBody DiagnosticoGestacionRequest request) {
        DiagnosticoGestacionResponseDTO actualizado = diagnosticoGestacionService.actualizar(id, request);
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable UUID id) {
        diagnosticoGestacionService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
