package com.camilolvz.ModuloSanidadGanado360.controller;


import com.camilolvz.ModuloSanidadGanado360.dto.RefuerzoRequestDTO;
import com.camilolvz.ModuloSanidadGanado360.dto.RefuerzoResponseDTO;
import com.camilolvz.ModuloSanidadGanado360.service.RefuerzoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/refuerzos")
@CrossOrigin("*")
public class RefuerzoController {

    private final RefuerzoService service;

    public RefuerzoController(RefuerzoService service) {
        this.service = service;
    }

    @GetMapping("/all")
    public ResponseEntity<List<RefuerzoResponseDTO>> listarTodos() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @PostMapping
    public ResponseEntity<RefuerzoResponseDTO> crear(@Valid @RequestBody RefuerzoRequestDTO req) {
        RefuerzoResponseDTO creado = service.crear(req);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RefuerzoResponseDTO> obtener(@PathVariable UUID id) {
        return service.obtener(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<RefuerzoResponseDTO> editar(@PathVariable UUID id, @Valid @RequestBody RefuerzoRequestDTO req) {
        return service.editar(id, req).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable UUID id) {
        if (service.eliminar(id)) return ResponseEntity.noContent().build();
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/vacuna/{vacunaId}")
    public ResponseEntity<List<RefuerzoResponseDTO>> porVacuna(@PathVariable UUID vacunaId) {
        return ResponseEntity.ok(service.buscarPorProducto(vacunaId));
    }
}
