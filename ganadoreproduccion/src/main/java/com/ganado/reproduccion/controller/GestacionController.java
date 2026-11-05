package com.ganado.reproduccion.controller;

import com.ganado.reproduccion.dto.GestacionRequest;
import com.ganado.reproduccion.dto.GestacionResponseDTO;
import com.ganado.reproduccion.service.GestacionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/gestaciones")
@RequiredArgsConstructor
public class GestacionController {

    private final GestacionService gestacionService;

    // Crear gestación
    @PostMapping
    public ResponseEntity<GestacionResponseDTO> crearGestacion(@RequestBody GestacionRequest request) {
        GestacionResponseDTO response = gestacionService.crearGestacion(request);
        return ResponseEntity.ok(response);
    }

    // Listar todas las gestaciones
    @GetMapping
    public ResponseEntity<List<GestacionResponseDTO>> listarTodas() {
        List<GestacionResponseDTO> gestaciones = gestacionService.listarTodas();
        return ResponseEntity.ok(gestaciones);
    }

    // Buscar gestación por ID
    @GetMapping("/{id}")
    public ResponseEntity<GestacionResponseDTO> buscarPorId(@PathVariable UUID id) {
        GestacionResponseDTO gestacion = gestacionService.buscarPorId(id);
        return ResponseEntity.ok(gestacion);
    }

    // Buscar gestaciones por hembra
    @GetMapping("/hembra/{idHembra}")
    public ResponseEntity<List<GestacionResponseDTO>> buscarPorHembra(@PathVariable UUID idHembra) {
        List<GestacionResponseDTO> gestaciones = gestacionService.buscarPorHembra(idHembra);
        return ResponseEntity.ok(gestaciones);
    }

    // Actualizar gestación
    @PutMapping("/{id}")
    public ResponseEntity<GestacionResponseDTO> actualizar(@PathVariable UUID id, @RequestBody GestacionRequest request) {
        GestacionResponseDTO actualizado = gestacionService.actualizar(id, request);
        return ResponseEntity.ok(actualizado);
    }

    // Eliminar gestación
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable UUID id) {
        gestacionService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
