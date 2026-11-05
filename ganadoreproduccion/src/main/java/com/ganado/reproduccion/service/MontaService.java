package com.ganado.reproduccion.service;

import com.ganado.reproduccion.dto.MontaRequest;
import com.ganado.reproduccion.dto.MontaResponseDTO;
import com.ganado.reproduccion.mapper.MontaMapper;
import com.ganado.reproduccion.model.Monta;
import com.ganado.reproduccion.repository.MontaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MontaService {

    private final MontaRepository montaRepository;

    // Registrar una nueva Monta
    public MontaResponseDTO registrarMonta(MontaRequest request) {

        // Validar que la hembra no tenga una monta activa
        if (montaRepository.existsByIdHembraAndEstado(request.getIdHembra(), Monta.EstadoMonta.ACTIVA)) {
            throw new IllegalStateException("La hembra ya tiene una monta activa.");
        }

        // Crear la Monta usando el mapper
        Monta monta = MontaMapper.toEntity(request);

        // Guardar en BD
        monta = montaRepository.save(monta);

        // Devolver DTO
        return MontaMapper.toDTO(monta);
    }

    // Obtener por ID
    public MontaResponseDTO obtenerPorId(UUID id) {
        Monta monta = montaRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Monta no encontrada"));

        return MontaMapper.toDTO(monta);
    }

    // Obtener todas las Montas
    public List<MontaResponseDTO> obtenerTodos() {
        return MontaMapper.toDTOList(montaRepository.findAll());
    }

    // Actualizar Monta
    public MontaResponseDTO actualizarMonta(UUID id, MontaRequest request) {
        Monta monta = montaRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Monta no encontrada"));

        // Actualizar campos
        monta.setIdHembra(request.getIdHembra());
        monta.setIdMacho(request.getIdMacho());
        monta.setFecha(request.getFecha());
        monta.setMetodoUtilizado(request.getMetodoUtilizado());
        monta.setNotas(request.getNotas());

        // Guardar cambios
        montaRepository.save(monta);

        return MontaMapper.toDTO(monta);
    }

    // Eliminar Monta
    public void eliminarMonta(UUID id) {
        Monta monta = montaRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Monta no encontrada"));

        // Actualizar campos
        monta.setEstado(Monta.EstadoMonta.FINALIZADA);

        // Guardar cambios
        montaRepository.save(monta);

    }
}
