package com.camilolvz.ModuloSanidadGanado360.controller;

import com.camilolvz.ModuloSanidadGanado360.dto.TratamientoRequestDTO;
import com.camilolvz.ModuloSanidadGanado360.dto.TratamientoResponseDTO;
import com.camilolvz.ModuloSanidadGanado360.service.TratamientoSanitarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/tratamientos")
@CrossOrigin("*")
public class TratamientoSanitarioController {

    private final TratamientoSanitarioService service;

    public TratamientoSanitarioController(TratamientoSanitarioService service) {
        this.service = service;
    }

    @GetMapping("/all")
    public ResponseEntity<List<TratamientoResponseDTO>> listarTodos() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @PostMapping
    public ResponseEntity<TratamientoResponseDTO> crear(@Valid @RequestBody TratamientoRequestDTO req) {
        TratamientoResponseDTO creado = service.crear(req);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TratamientoResponseDTO> obtenerPorId(@PathVariable UUID id) {
        return service.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<TratamientoResponseDTO> editar(@PathVariable UUID id,
                                                         @Valid @RequestBody TratamientoRequestDTO req) {
        return service.editar(id, req)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable UUID id) {
        boolean eliminado = service.eliminar(id);
        if (eliminado) return ResponseEntity.noContent().build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping("/buscar")
    public ResponseEntity<TratamientoResponseDTO> buscarPorNombre(@RequestParam String nombre) {
        return service.buscarPorNombre(nombre)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping("/buscar/descripcion")
    public ResponseEntity<List<TratamientoResponseDTO>> buscarPorDescripcion(@RequestParam String q) {
        return ResponseEntity.ok(service.buscarPorDescripcion(q));
    }
}
