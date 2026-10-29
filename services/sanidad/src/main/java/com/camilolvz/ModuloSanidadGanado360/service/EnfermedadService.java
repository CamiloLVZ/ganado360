package com.camilolvz.ModuloSanidadGanado360.service;

import com.camilolvz.ModuloSanidadGanado360.dto.EnfermedadRequestDTO;
import com.camilolvz.ModuloSanidadGanado360.dto.EnfermedadResponseDTO;
import com.camilolvz.ModuloSanidadGanado360.mapper.EnfermedadMapper;
import com.camilolvz.ModuloSanidadGanado360.model.Enfermedad;
import com.camilolvz.ModuloSanidadGanado360.repository.EnfermedadRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class EnfermedadService {

    private final EnfermedadRepository repository;
    private final EnfermedadMapper mapper;

    public EnfermedadService(EnfermedadRepository repository, EnfermedadMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<EnfermedadResponseDTO> listarTodas() {
        return repository.findAll().stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    public EnfermedadResponseDTO crear(EnfermedadRequestDTO req) {
        Enfermedad existente = repository.findByNombreIgnoreCase(req.getNombre());
        if (existente != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Ya existe una enfermedad con ese nombre");
        }

        Enfermedad nueva = mapper.toEntity(req);
        Enfermedad saved = repository.save(nueva);
        return mapper.toDTO(saved);
    }

    public Optional<EnfermedadResponseDTO> obtenerPorId(UUID id) {
        return repository.findById(id).map(mapper::toDTO);
    }

    public Optional<EnfermedadResponseDTO> editar(UUID id, EnfermedadRequestDTO req) {
        return repository.findById(id).map(existing -> {

            String nuevoNombre = req.getNombre();
            if (!nuevoNombre.equalsIgnoreCase(existing.getNombre())) {
                Enfermedad e = repository.findByNombreIgnoreCase(nuevoNombre);
                if (e != null && !e.getId().equals(existing.getId())) {
                    throw new ResponseStatusException(HttpStatus.CONFLICT, "Nombre duplicado");
                }
            }

            existing.setNombre(req.getNombre());
            existing.setDescripcion(req.getDescripcion());

            return mapper.toDTO(repository.save(existing));
        });
    }

    public boolean eliminar(UUID id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }
}
