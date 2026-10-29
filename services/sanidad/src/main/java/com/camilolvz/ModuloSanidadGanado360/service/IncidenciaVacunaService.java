package com.camilolvz.ModuloSanidadGanado360.service;

import com.camilolvz.ModuloSanidadGanado360.dto.IncidenciaVacunaRequestDTO;
import com.camilolvz.ModuloSanidadGanado360.dto.IncidenciaVacunaResponseDTO;
import com.camilolvz.ModuloSanidadGanado360.mapper.IncidenciaVacunaMapper;
import com.camilolvz.ModuloSanidadGanado360.model.EstadoIncidencia;
import com.camilolvz.ModuloSanidadGanado360.model.IncidenciaVacuna;
import com.camilolvz.ModuloSanidadGanado360.model.ProductoSanitario;
import com.camilolvz.ModuloSanidadGanado360.repository.IncidenciaVacunaRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class IncidenciaVacunaService {

    private final IncidenciaVacunaRepository repository;
    private final ProductoSanitarioService productoService;
    private final IncidenciaVacunaMapper mapper;
    private final RefuerzoService refuerzoService; // <- inyectado

    public IncidenciaVacunaService(IncidenciaVacunaRepository repository,
                                   ProductoSanitarioService productoService,
                                   IncidenciaVacunaMapper mapper,
                                   RefuerzoService refuerzoService) {
        this.repository = repository;
        this.productoService = productoService;
        this.mapper = mapper;
        this.refuerzoService = refuerzoService;
    }

    // Crear
    @Transactional
    public IncidenciaVacunaResponseDTO crear(IncidenciaVacunaRequestDTO req) {
        if (req.getFechaVacunacion() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "fechaVacunacion es obligatoria");
        }

        ProductoSanitario producto = productoService.obtenerEntityPorId(req.getProductoid())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "Producto sanitario no encontrado con id: " + req.getProductoid()));

        IncidenciaVacuna entidad = mapper.toEntity(req, producto);

        // Guardar la incidencia base
        IncidenciaVacuna savedBase = repository.save(entidad);

        // Generar incidencias pendientes para cada refuerzo del producto
        try {
            // RefuerzoService.generarIncidenciasPorRefuerzos retorna la lista de incidencias creadas
            refuerzoService.generarIncidenciasPorRefuerzos(savedBase);
        } catch (Exception ex) {
            // Lanzar excepción para provocar rollback de la transacción
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error generando incidencias por refuerzos: " + ex.getMessage());
        }

        return mapper.toDto(savedBase);
    }

    // Listar todos
    public List<IncidenciaVacunaResponseDTO> listarTodos() {
        return repository.findAll().stream().map(mapper::toDto).collect(Collectors.toList());
    }

    // Obtener por id
    public IncidenciaVacunaResponseDTO obtener(UUID id) {
        return repository.findById(id)
                .map(mapper::toDto)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Incidencia no encontrada"));
    }

    // Obtener por animal
    public List<IncidenciaVacunaResponseDTO> obtenerPorAnimal(UUID idAnimal) {
        return repository.findByIdAnimal(idAnimal).stream().map(mapper::toDto).collect(Collectors.toList());
    }

    // Obtener por estado
    public List<IncidenciaVacunaResponseDTO> obtenerPorEstado(EstadoIncidencia estado) {
        return repository.findByEstado(estado).stream().map(mapper::toDto).collect(Collectors.toList());
    }

    // Actualizar (parcial)
    public IncidenciaVacunaResponseDTO actualizar(UUID id, IncidenciaVacunaRequestDTO req) {
        IncidenciaVacuna existente = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Incidencia no encontrada"));

        ProductoSanitario producto = null;
        if (req.getProductoid() != null) {
            producto = productoService.obtenerEntityPorId(req.getProductoid())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Producto sanitario no encontrado con id: " + req.getProductoid()));
        }

        mapper.updateEntityFromDto(existente, req, producto);

        IncidenciaVacuna guardada = repository.save(existente);
        return mapper.toDto(guardada);
    }

    // "Eliminar" lógico: cambiar estado a ANULADO (no borrar registro)
    public IncidenciaVacunaResponseDTO anular(UUID id) {
        IncidenciaVacuna existente = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Incidencia no encontrada"));

        existente.setEstado(EstadoIncidencia.ANULADO);
        IncidenciaVacuna guardada = repository.save(existente);
        return mapper.toDto(guardada);
    }

    // Buscar por productoId
    public List<IncidenciaVacunaResponseDTO> obtenerPorProducto(UUID productoId) {
        return repository.findByProductoUsadoId(productoId).stream().map(mapper::toDto).collect(Collectors.toList());
    }
}
