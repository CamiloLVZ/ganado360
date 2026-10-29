package com.camilolvz.ModuloSanidadGanado360.service;

import com.camilolvz.ModuloSanidadGanado360.dto.TratamientoRequestDTO;
import com.camilolvz.ModuloSanidadGanado360.dto.TratamientoResponseDTO;
import com.camilolvz.ModuloSanidadGanado360.mapper.TratamientoMapper;
import com.camilolvz.ModuloSanidadGanado360.model.TratamientoSanitario;
import com.camilolvz.ModuloSanidadGanado360.repository.TratamientoSanitarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class TratamientoSanitarioService {

    private final TratamientoSanitarioRepository repository;
    private final TratamientoMapper mapper;

    public TratamientoSanitarioService(TratamientoSanitarioRepository repository,
                                       TratamientoMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    // Listar todos
    public List<TratamientoResponseDTO> listarTodos() {
        return repository.findAll().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    // Crear
    public TratamientoResponseDTO crear(TratamientoRequestDTO req) {
        // Validar unicidad por nombre
        TratamientoSanitario existente = repository.findByNombreIgnoreCase(req.getNombre());
        if (existente != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Ya existe un tratamiento con ese nombre");
        }

        TratamientoSanitario t = mapper.toEntity(req);
        TratamientoSanitario saved = repository.save(t);
        return mapper.toDto(saved);
    }

    // Obtener por id
    public Optional<TratamientoResponseDTO> obtenerPorId(UUID id) {
        return repository.findById(id).map(mapper::toDto);
    }

    // Editar
    public Optional<TratamientoResponseDTO> editar(UUID id, TratamientoRequestDTO req) {
        return repository.findById(id).map(existing -> {
            String nuevoNombre = req.getNombre();
            if (nuevoNombre != null && !nuevoNombre.equalsIgnoreCase(existing.getNombre())) {
                TratamientoSanitario porNombre = repository.findByNombreIgnoreCase(nuevoNombre);
                if (porNombre != null && !porNombre.getId().equals(existing.getId())) {
                    throw new ResponseStatusException(HttpStatus.CONFLICT, "Otro tratamiento ya usa ese nombre");
                }
            }

            mapper.updateEntityFromRequest(existing, req);
            TratamientoSanitario updated = repository.save(existing);
            return mapper.toDto(updated);
        });
    }

    // Eliminar
    public boolean eliminar(UUID id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    // Buscar por nombre (exacto, case-insensitive)
    public Optional<TratamientoResponseDTO> buscarPorNombre(String nombre) {
        TratamientoSanitario e = repository.findByNombreIgnoreCase(nombre);
        return e != null ? Optional.of(mapper.toDto(e)) : Optional.empty();
    }

    // Buscar por fragmento en descripcion
    public List<TratamientoResponseDTO> buscarPorDescripcion(String fragment) {
        return repository.findByDescripcionContainingIgnoreCase(fragment)
                .stream().map(mapper::toDto).collect(Collectors.toList());
    }
}
