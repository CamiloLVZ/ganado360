package com.camilolvz.ModuloSanidadGanado360.controller;

import com.camilolvz.ModuloSanidadGanado360.dto.IncidenciaTratamientoRequestDTO;
import com.camilolvz.ModuloSanidadGanado360.dto.IncidenciaTratamientoResponseDTO;
import com.camilolvz.ModuloSanidadGanado360.model.EstadoIncidencia;
import com.camilolvz.ModuloSanidadGanado360.service.IncidenciaTratamientoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/incidencias-tratamientos")
@CrossOrigin("*")
public class IncidenciaTratamientoController {

    private final IncidenciaTratamientoService service;

    public IncidenciaTratamientoController(IncidenciaTratamientoService service) {
        this.service = service;
    }

    @GetMapping("/all")
    public ResponseEntity<List<IncidenciaTratamientoResponseDTO>> listarTodos() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @PostMapping
    public ResponseEntity<IncidenciaTratamientoResponseDTO> crear(@Valid @RequestBody IncidenciaTratamientoRequestDTO req) {
        System.out.println(req.getIncidenciaEnfermedadId());

        if (req.getIdAnimal() == null || req.getTratamientoId() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        boolean hayActivas = service.hayIncidenciasActivas(req.getIdAnimal(), req.getTratamientoId());
        if (hayActivas) {
            // 409 Conflict — ya hay incidencias activas para este animal y tratamiento
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(null);
        }


        IncidenciaTratamientoResponseDTO creado = service.crear(req);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @GetMapping("/{id}")
    public ResponseEntity<IncidenciaTratamientoResponseDTO> obtener(@PathVariable UUID id) {
        return ResponseEntity.ok(service.obtener(id));
    }

    @GetMapping("/animal/{idAnimal}")
    public ResponseEntity<List<IncidenciaTratamientoResponseDTO>> obtenerPorAnimal(@PathVariable UUID idAnimal) {
        return ResponseEntity.ok(service.obtenerPorAnimal(idAnimal));
    }

    @GetMapping("/tratamiento/{tratamientoId}")
    public ResponseEntity<List<IncidenciaTratamientoResponseDTO>> obtenerPorTratamiento(@PathVariable UUID tratamientoId) {
        return ResponseEntity.ok(service.obtenerPorTratamiento(tratamientoId));
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<IncidenciaTratamientoResponseDTO>> obtenerPorEstado(@PathVariable String estado) {
        try {
            EstadoIncidencia e = EstadoIncidencia.valueOf(estado.toUpperCase());
            return ResponseEntity.ok(service.obtenerPorEstado(e));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<IncidenciaTratamientoResponseDTO> actualizar(@PathVariable UUID id,
                                                                       @Valid @RequestBody IncidenciaTratamientoRequestDTO req) {
        IncidenciaTratamientoResponseDTO actualizado = service.actualizar(id, req);
        return ResponseEntity.ok(actualizado);
    }

    /**
     * DELETE lógico: no borra la fila, solo la marca como ANULADO.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<IncidenciaTratamientoResponseDTO> anular(@PathVariable UUID id) {
        IncidenciaTratamientoResponseDTO anulado = service.anular(id);
        return ResponseEntity.ok(anulado);
    }
}
