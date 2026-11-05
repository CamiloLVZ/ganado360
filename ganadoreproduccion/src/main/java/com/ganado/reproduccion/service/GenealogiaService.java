package com.ganado.reproduccion.service;

import com.ganado.reproduccion.dto.GenealogiaRequestDTO;
import com.ganado.reproduccion.dto.GenealogiaResponseDTO;
import com.ganado.reproduccion.mapper.GenealogiaMapper;
import com.ganado.reproduccion.model.Genealogia;
import com.ganado.reproduccion.repository.GenealogiaRepository;
import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class GenealogiaService {

    private final GenealogiaRepository repo;
    private final GenealogiaMapper mapper;

    public GenealogiaService(GenealogiaRepository repo, GenealogiaMapper mapper) {
        this.repo = repo;
        this.mapper = mapper;
    }

    public GenealogiaResponseDTO crear(GenealogiaRequestDTO request) {
        // Si ya existe una genealogía para ese hijo, podemos decidir sobrescribir o lanzar error.
        // Aquí lanzamos error si ya existe.
        UUID hijoId = request.getHijo();
        if (hijoId == null) {
            throw new IllegalArgumentException("hijo no puede ser null");
        }
        if (repo.existsById(hijoId)) {
            throw new IllegalArgumentException("Genealogía para el hijo ya existe: " + hijoId);
        }
        Genealogia entity = mapper.toEntity(request);
        Genealogia saved = repo.save(entity);
        return mapper.toResponse(saved);
    }

    public List<GenealogiaResponseDTO> listarTodas() {
        return repo.findAll().stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    public GenealogiaResponseDTO obtenerPorHijo(UUID hijo) {
        return repo.findById(hijo)
                .map(mapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Genealogía no encontrada para hijo: " + hijo));
    }

    public List<GenealogiaResponseDTO> obtenerPorMadre(UUID madre) {
        return repo.findByMadre(madre).stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    public GenealogiaResponseDTO actualizar(UUID hijo, GenealogiaRequestDTO request) {
        Genealogia entity = repo.findById(hijo)
                .orElseThrow(() -> new ResourceNotFoundException("Genealogía no encontrada para hijo: " + hijo));
        // Actualizamos madre y padre (no cambiamos PK hijo)
        mapper.updateEntityFromDto(request, entity);
        Genealogia saved = repo.save(entity);
        return mapper.toResponse(saved);
    }

    public void eliminar(UUID hijo) {
        if (!repo.existsById(hijo)) {
            throw new ResourceNotFoundException("Genealogía no encontrada para hijo: " + hijo);
        }
        repo.deleteById(hijo);
    }
}