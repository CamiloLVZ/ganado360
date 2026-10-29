package com.camilolvz.ModuloSanidadGanado360.controller;

import com.camilolvz.ModuloSanidadGanado360.dto.EnfermedadRequestDTO;
import com.camilolvz.ModuloSanidadGanado360.dto.EnfermedadResponseDTO;
import com.camilolvz.ModuloSanidadGanado360.service.EnfermedadService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/enfermedades")
public class EnfermedadController {

    private final EnfermedadService service;

    public EnfermedadController(EnfermedadService service) {
        this.service = service;
    }

    // Listar todos (response DTO)
    @GetMapping("/all")
    public ResponseEntity<List<EnfermedadResponseDTO>> listarEnfermedades() {
        return ResponseEntity.ok(service.listarTodas());
    }

    // Crear
    @PostMapping
    public ResponseEntity<EnfermedadResponseDTO> crear(@Valid @RequestBody EnfermedadRequestDTO req) {
        EnfermedadResponseDTO creado = service.crear(req);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    // Obtener por id
    @GetMapping("/{id}")
    public ResponseEntity<EnfermedadResponseDTO> obtenerPorId(@PathVariable UUID id) {
        return service.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // Editar
    @PutMapping("/{id}")
    public ResponseEntity<EnfermedadResponseDTO> editar(@PathVariable UUID id,
                                                        @Valid @RequestBody EnfermedadRequestDTO req) {
        return service.editar(id, req)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // Eliminar
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable UUID id) {
        boolean eliminado = service.eliminar(id);
        if (eliminado) return ResponseEntity.noContent().build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }


}
