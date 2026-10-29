package com.camilolvz.ModuloSanidadGanado360.controller;

import com.camilolvz.ModuloSanidadGanado360.dto.IncidenciaEnfermedadRequestDTO;
import com.camilolvz.ModuloSanidadGanado360.dto.IncidenciaEnfermedadResponseDTO;
import com.camilolvz.ModuloSanidadGanado360.service.IncidenciaEnfermedadService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/incidencias-enfermedad")
@CrossOrigin("*")
public class IncidenciaEnfermedadController {

    private final IncidenciaEnfermedadService service;

    public IncidenciaEnfermedadController(IncidenciaEnfermedadService service) {
        this.service = service;
    }

    @GetMapping("/all")
    public ResponseEntity<List<IncidenciaEnfermedadResponseDTO>> listarTodos() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @PostMapping
    public ResponseEntity<IncidenciaEnfermedadResponseDTO> crear(@Valid @RequestBody IncidenciaEnfermedadRequestDTO req) {
        IncidenciaEnfermedadResponseDTO creado = service.crear(req);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @GetMapping("/{id}")
    public ResponseEntity<IncidenciaEnfermedadResponseDTO> obtener(@PathVariable UUID id) {
        return ResponseEntity.ok(service.obtener(id));
    }

    @GetMapping("/animal/{idAnimal}")
    public ResponseEntity<List<IncidenciaEnfermedadResponseDTO>> obtenerPorAnimal(@PathVariable UUID idAnimal) {
        return ResponseEntity.ok(service.obtenerPorAnimal(idAnimal));
    }

    @GetMapping("/enfermedad/{enfermedadId}")
    public ResponseEntity<List<IncidenciaEnfermedadResponseDTO>> obtenerPorEnfermedad(@PathVariable UUID enfermedadId) {
        return ResponseEntity.ok(service.obtenerPorEnfermedad(enfermedadId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<IncidenciaEnfermedadResponseDTO> actualizar(@PathVariable UUID id,
                                                                      @Valid @RequestBody IncidenciaEnfermedadRequestDTO req) {
        IncidenciaEnfermedadResponseDTO actualizado = service.actualizar(id, req);
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable UUID id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
