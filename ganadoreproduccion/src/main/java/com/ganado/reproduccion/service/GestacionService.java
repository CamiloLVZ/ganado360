package com.ganado.reproduccion.service;

import com.ganado.reproduccion.dto.GestacionRequest;
import com.ganado.reproduccion.dto.GestacionResponseDTO;
import com.ganado.reproduccion.model.Gestacion;
import com.ganado.reproduccion.mapper.GestacionMapper;
import com.ganado.reproduccion.repository.GestacionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GestacionService {

    private final GestacionRepository gestacionRepository;

    // Crear gestación
    public GestacionResponseDTO crearGestacion(GestacionRequest request) {
        Gestacion gestacion = GestacionMapper.toEntity(request);
        gestacionRepository.save(gestacion);
        return GestacionMapper.toDTO(gestacion);
    }

    // Listar todas las gestaciones
    public List<GestacionResponseDTO> listarTodas() {
        return gestacionRepository.findAll()
                .stream()
                .map(GestacionMapper::toDTO)
                .toList();
    }

    // Buscar gestación por ID
    public GestacionResponseDTO buscarPorId(UUID id) {
        Gestacion gestacion = gestacionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Gestación no encontrada"));
        return GestacionMapper.toDTO(gestacion);
    }

    // Buscar gestaciones por hembra
    public List<GestacionResponseDTO> buscarPorHembra(UUID idHembra) {
        return gestacionRepository.findByIdHembra(idHembra)
                .stream()
                .map(GestacionMapper::toDTO)
                .toList();
    }

    // Actualizar gestación
    public GestacionResponseDTO actualizar(UUID id, GestacionRequest request) {
        Gestacion gestacion = gestacionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Gestación no encontrada"));

        gestacion.setFechaInicio(request.getFechaInicio());
        gestacion.setFechaEstimadaParto(request.getFechaEstimadaParto());
        if (request.getEstado() != null) {
            gestacion.setEstado(Gestacion.EstadoGestacion.valueOf(request.getEstado().toUpperCase()));
        }

        gestacionRepository.save(gestacion);
        return GestacionMapper.toDTO(gestacion);
    }

    // Eliminar gestación
    public void eliminar(UUID id) {
        if (!gestacionRepository.existsById(id)) {
            throw new IllegalArgumentException("Gestación no encontrada");
        }
        gestacionRepository.deleteById(id);
    }
}