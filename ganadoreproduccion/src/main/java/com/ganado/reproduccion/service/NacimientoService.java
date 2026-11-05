package com.ganado.reproduccion.service;

import com.ganado.reproduccion.dto.NacimientoRequestDTO;
import com.ganado.reproduccion.dto.NacimientoResponseDTO;
import com.ganado.reproduccion.mapper.NacimientoMapper;
import com.ganado.reproduccion.model.Nacimiento;
import com.ganado.reproduccion.repository.NacimientoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NacimientoService {

    private final NacimientoRepository nacimientoRepository;

    public NacimientoResponseDTO registrarNacimiento(NacimientoRequestDTO request) {

        // Mapear request → entidad
        Nacimiento nacimiento = NacimientoMapper.toEntity(request);

        // Guardar en BD
        nacimiento = nacimientoRepository.save(nacimiento);

        // Responder DTO
        return NacimientoMapper.toDTO(nacimiento);
    }

    public NacimientoResponseDTO obtenerPorId(UUID id) {
        Nacimiento nacimiento = nacimientoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Nacimiento no encontrado"));

        return NacimientoMapper.toDTO(nacimiento);
    }

    public List<NacimientoResponseDTO> obtenerTodos() {
        return nacimientoRepository.findAll()
                .stream()
                .map(NacimientoMapper::toDTO)
                .toList();
    }

    public List<NacimientoResponseDTO> obtenerPorIdMadre(UUID idMadre) {
        return nacimientoRepository.findByIdMadre(idMadre)
                .stream()
                .map(NacimientoMapper::toDTO)
                .toList();
    }

    public NacimientoResponseDTO actualizar(UUID id, NacimientoRequestDTO request) {
        Nacimiento nacimiento = nacimientoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Nacimiento no encontrado"));

        nacimiento.setIdMonta(request.getIdMonta());
        nacimiento.setIdMadre(request.getIdMadre());
        nacimiento.setIdAnimal(request.getIdAnimal());
        nacimiento.setFecha(request.getFecha());
        nacimiento.setSexo(request.getSexo());
        nacimiento.setPeso(request.getPeso());
        nacimiento.setObservaciones(request.getObservaciones());

        nacimientoRepository.save(nacimiento);

        return NacimientoMapper.toDTO(nacimiento);
    }

    public void eliminar(UUID id) {
        nacimientoRepository.deleteById(id);
    }
}
