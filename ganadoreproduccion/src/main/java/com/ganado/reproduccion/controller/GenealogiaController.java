package com.ganado.reproduccion.controller;

import com.ganado.reproduccion.dto.GenealogiaRequestDTO;
import com.ganado.reproduccion.dto.GenealogiaResponseDTO;
import com.ganado.reproduccion.service.GenealogiaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/genealogias")
public class GenealogiaController {

    private final GenealogiaService service;

    public GenealogiaController(GenealogiaService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<GenealogiaResponseDTO> crear(@Valid @RequestBody GenealogiaRequestDTO request) {
        GenealogiaResponseDTO created = service.crear(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping
    public ResponseEntity<List<GenealogiaResponseDTO>> listarTodas() {
        return ResponseEntity.ok(service.listarTodas());
    }

    @GetMapping("/{hijoId}")
    public ResponseEntity<GenealogiaResponseDTO> obtenerPorHijo(@PathVariable("hijoId") UUID hijoId) {
        return ResponseEntity.ok(service.obtenerPorHijo(hijoId));
    }

    @GetMapping("/madre/{madreId}")
    public ResponseEntity<List<GenealogiaResponseDTO>> obtenerPorMadre(@PathVariable("madreId") UUID madreId) {
        return ResponseEntity.ok(service.obtenerPorMadre(madreId));
    }

    @PutMapping("/{hijoId}")
    public ResponseEntity<GenealogiaResponseDTO> actualizar(
            @PathVariable("hijoId") UUID hijoId,
            @Valid @RequestBody GenealogiaRequestDTO request) {

        return ResponseEntity.ok(service.actualizar(hijoId, request));
    }

    @DeleteMapping("/{hijoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable("hijoId") UUID hijoId) {
        service.eliminar(hijoId);
    }
}