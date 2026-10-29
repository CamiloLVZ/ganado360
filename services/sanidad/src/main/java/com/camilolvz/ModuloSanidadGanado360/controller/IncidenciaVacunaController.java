package com.camilolvz.ModuloSanidadGanado360.controller;

import com.camilolvz.ModuloSanidadGanado360.dto.IncidenciaVacunaRequestDTO;
import com.camilolvz.ModuloSanidadGanado360.dto.IncidenciaVacunaResponseDTO;
import com.camilolvz.ModuloSanidadGanado360.model.EstadoIncidencia;
import com.camilolvz.ModuloSanidadGanado360.service.IncidenciaVacunaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/incidencias-vacunas")
@CrossOrigin("*")
public class IncidenciaVacunaController {

    private final IncidenciaVacunaService service;

    public IncidenciaVacunaController(IncidenciaVacunaService service) {
        this.service = service;
    }

    @GetMapping("/all")
    public ResponseEntity<List<IncidenciaVacunaResponseDTO>> listarTodos() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @PostMapping
    public ResponseEntity<IncidenciaVacunaResponseDTO> crear(@Valid @RequestBody IncidenciaVacunaRequestDTO req) {
        IncidenciaVacunaResponseDTO creado = service.crear(req);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @GetMapping("/{id}")
    public ResponseEntity<IncidenciaVacunaResponseDTO> obtener(@PathVariable UUID id) {
        return ResponseEntity.ok(service.obtener(id));
    }

    @GetMapping("/animal/{idAnimal}")
    public ResponseEntity<List<IncidenciaVacunaResponseDTO>> obtenerPorAnimal(@PathVariable UUID idAnimal) {
        return ResponseEntity.ok(service.obtenerPorAnimal(idAnimal));
    }

    @GetMapping("/producto/{productoId}")
    public ResponseEntity<List<IncidenciaVacunaResponseDTO>> obtenerPorProducto(@PathVariable UUID productoId) {
        return ResponseEntity.ok(service.obtenerPorProducto(productoId));
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<IncidenciaVacunaResponseDTO>> obtenerPorEstado(@PathVariable String estado) {
        try {
            EstadoIncidencia e = EstadoIncidencia.valueOf(estado.toUpperCase());
            return ResponseEntity.ok(service.obtenerPorEstado(e));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<IncidenciaVacunaResponseDTO> actualizar(@PathVariable UUID id,
                                                                  @Valid @RequestBody IncidenciaVacunaRequestDTO req) {
        IncidenciaVacunaResponseDTO actualizado = service.actualizar(id, req);
        return ResponseEntity.ok(actualizado);
    }

    /**
     * DELETE lógico: no elimina la fila, solo la marca como ANULADO.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<IncidenciaVacunaResponseDTO> anular(@PathVariable UUID id) {
        IncidenciaVacunaResponseDTO anulado = service.anular(id);
        return ResponseEntity.ok(anulado);
    }
}
