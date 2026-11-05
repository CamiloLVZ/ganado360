package com.ganado.inventario.service;

import com.ganado.inventario.dto.AnimalRequestDTO;
import com.ganado.inventario.dto.AnimalResponseDTO;
import com.ganado.inventario.mapper.AnimalMapper;
import com.ganado.inventario.model.Animal;
import com.ganado.inventario.model.Finca;
import com.ganado.inventario.repository.AnimalRepository;
import com.ganado.inventario.repository.FincaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AnimalServiceImpl implements AnimalService {

    private final AnimalRepository animalRepository;
    private final FincaRepository fincaRepository;
    private final AnimalMapper animalMapper;

    private Finca getFincaOrThrow(UUID fincaId) {
        return fincaRepository.findById(fincaId)
                .orElseThrow(() -> new RuntimeException("Finca no encontrada con ID: " + fincaId));
    }

    @Override
    public AnimalResponseDTO crear(AnimalRequestDTO dto) {
        if (dto.getEstado() == null) {
            dto.setEstado("Activo");
        }

        Finca finca = getFincaOrThrow(dto.getFincaId());
        Animal animal = animalMapper.toEntity(dto, finca);
        return animalMapper.toResponse(animalRepository.save(animal));
    }

    @Override
    public AnimalResponseDTO obtenerPorId(UUID id) {
        Animal animal = animalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Animal no encontrado con ID: " + id));
        return animalMapper.toResponse(animal);
    }

    @Override
    public List<AnimalResponseDTO> listarTodos() {
        return animalRepository.findAll()
                .stream()
                .map(animalMapper::toResponse)
                .toList();
    }

    @Override
    public List<AnimalResponseDTO> listarPorFinca(UUID fincaId) {
        return listarPorFinca(fincaId, null);
    }

    @Override
    public List<AnimalResponseDTO> listarPorFinca(UUID fincaId, String userId) {
        Finca finca = getFincaOrThrow(fincaId);

        if (userId != null && !userId.isBlank()) {
            try {
                UUID userUuid = UUID.fromString(userId);
                boolean esCreador = finca.getUsuarioCreadorId() != null && finca.getUsuarioCreadorId().equals(userUuid);
                boolean esMiembro = finca.getUsuarioMiembroIds() != null && finca.getUsuarioMiembroIds().contains(userUuid);

                if (!esCreador && !esMiembro) {
                    throw new RuntimeException("Acceso denegado: el usuario no pertenece a esta finca");
                }
            } catch (IllegalArgumentException ignored) {
            }
        }

        return animalRepository.findByFincaId(fincaId)
                .stream()
                .map(animalMapper::toResponse)
                .toList();
    }

    @Override
    public AnimalResponseDTO actualizar(UUID id, AnimalRequestDTO dto) {
        Animal existente = animalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Animal no encontrado con ID: " + id));

        Finca finca = getFincaOrThrow(dto.getFincaId());

        animalMapper.updateEntity(existente, dto, finca);

        return animalMapper.toResponse(animalRepository.save(existente));
    }

    @Override
    public void eliminar(UUID id) {
        Animal existente = animalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Animal no encontrado con ID: " + id));

        existente.setEstado("Inactivo");
        animalRepository.save(existente);
    }

    @Override
    public List<AnimalResponseDTO> obtenerPorEspecie(String especie) {
        if (especie == null || especie.trim().isEmpty()) {
            throw new IllegalArgumentException("La especie no puede ser nula o vacía");
        }

        return animalRepository.findByEspecieIgnoreCase(especie.trim())
                .stream()
                .map(animalMapper::toResponse)
                .toList();
    }
}
