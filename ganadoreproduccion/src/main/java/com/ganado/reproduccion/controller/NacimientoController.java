package com.ganado.reproduccion.controller;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import com.ganado.reproduccion.dto.NacimientoRequestDTO;
import com.ganado.reproduccion.dto.NacimientoResponseDTO;
import com.ganado.reproduccion.service.NacimientoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/nacimientos")
@RequiredArgsConstructor
public class NacimientoController {

    private final NacimientoService nacimientoService;

    @PostMapping
    public ResponseEntity<NacimientoResponseDTO> crearNacimiento(
            @Valid @RequestBody NacimientoRequestDTO request
    ) {
        return ResponseEntity.ok(nacimientoService.registrarNacimiento(request));
    }

    @GetMapping
    public ResponseEntity<List<NacimientoResponseDTO>> obtenerTodos() {
        return ResponseEntity.ok(nacimientoService.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<NacimientoResponseDTO> obtenerPorId(@PathVariable UUID id) {
        return ResponseEntity.ok(nacimientoService.obtenerPorId(id));
    }

    @GetMapping("/madre/{idMadre}")
    public ResponseEntity<List<NacimientoResponseDTO>> obtenerPorIdMadre(@PathVariable UUID idMadre) {
        return ResponseEntity.ok(nacimientoService.obtenerPorIdMadre(idMadre));
    }

    @PutMapping("/{id}")
    public ResponseEntity<NacimientoResponseDTO> actualizar(
            @PathVariable UUID id,
            @Valid @RequestBody NacimientoRequestDTO request
    ) {
        return ResponseEntity.ok(nacimientoService.actualizar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable UUID id) {
        nacimientoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
