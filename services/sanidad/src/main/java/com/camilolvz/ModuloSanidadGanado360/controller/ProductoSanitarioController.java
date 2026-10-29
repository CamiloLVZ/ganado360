package com.camilolvz.ModuloSanidadGanado360.controller;

import com.camilolvz.ModuloSanidadGanado360.dto.ProductoSanitarioRequestDTO;
import com.camilolvz.ModuloSanidadGanado360.dto.ProductoSanitarioResponseDTO;
import com.camilolvz.ModuloSanidadGanado360.service.ProductoSanitarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/productos-sanitarios")
public class ProductoSanitarioController {

    private final ProductoSanitarioService service;
    public ProductoSanitarioController(ProductoSanitarioService service) {
        this.service = service;
    }

    // Listar todos
    @GetMapping("/all")
    public ResponseEntity<List<ProductoSanitarioResponseDTO>> listarProductos() {
        return ResponseEntity.ok(service.listarTodos());
    }

    // Crear
    @PostMapping
    public ResponseEntity<ProductoSanitarioResponseDTO> crearProducto(@RequestBody ProductoSanitarioRequestDTO dto) {
        ProductoSanitarioResponseDTO creado = service.crear(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    // Obtener por id
    @GetMapping("/{id}")
    public ResponseEntity<ProductoSanitarioResponseDTO> obtenerPorId(@PathVariable UUID id) {
        return service.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // Editar
    @PutMapping("/{id}")
    public ResponseEntity<ProductoSanitarioResponseDTO> editarProducto(@PathVariable UUID id, @RequestBody ProductoSanitarioRequestDTO dto) {
        return service.editar(id, dto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // Eliminar
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProducto(@PathVariable UUID id) {
        boolean eliminado = service.eliminar(id);
        if (eliminado) return ResponseEntity.noContent().build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    // Obtener por especie
    @GetMapping("/especie/{especie}")
    public ResponseEntity<List<ProductoSanitarioResponseDTO>> obtenerPorEspecie(@PathVariable String especie) {
        List<ProductoSanitarioResponseDTO> list = service.buscarPorEspecie(especie);
        return ResponseEntity.ok(list);
    }

    // Buscar por nombre (opcional)
    @GetMapping("/buscar")
    public ResponseEntity<ProductoSanitarioResponseDTO> buscarPorNombre(@RequestParam String nombre) {
        ProductoSanitarioResponseDTO dto = service.buscarPorNombre(nombre);
        if (dto == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return ResponseEntity.ok(dto);
    }
}
