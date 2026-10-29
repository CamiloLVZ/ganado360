package com.ganado.reproduccion.service;

import com.ganado.reproduccion.dto.DiagnosticoGestacionRequest;
import com.ganado.reproduccion.dto.DiagnosticoGestacionResponseDTO;
import com.ganado.reproduccion.mapper.DiagnosticoGestacionMapper;
import com.ganado.reproduccion.model.DiagnosticoGestacion;
import com.ganado.reproduccion.model.Gestacion;
import com.ganado.reproduccion.model.Monta;
import com.ganado.reproduccion.repository.DiagnosticoGestacionRepository;
import com.ganado.reproduccion.repository.GestacionRepository;
import com.ganado.reproduccion.repository.MontaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DiagnosticoGestacionService {

    private final DiagnosticoGestacionRepository diagnosticoGestacionRepository;
    private final GestacionRepository gestacionRepository;
    private final MontaService montaService;
    private final MontaRepository montaRepository;

    public DiagnosticoGestacionResponseDTO registrarDiagnostico(DiagnosticoGestacionRequest request) {
        // Crear DiagnosticoGestacion usando mapper
        DiagnosticoGestacion diagnostico = DiagnosticoGestacionMapper.toEntity(request);
        diagnosticoGestacionRepository.save(diagnostico);

        // Crear gestación si es GESTANTE
        if (request.getResultado() == DiagnosticoGestacion.Resultado.GESTANTE) {
            UUID idHembra = obtenerIdHembraDeMonta(request.getIdMonta());
            Gestacion gestacion = DiagnosticoGestacionMapper.toGestacion(request, idHembra);
            gestacionRepository.save(gestacion);
        }

        if (request.getResultado() == DiagnosticoGestacion.Resultado.VACIA) {
            montaService.eliminarMonta(request.getIdMonta());
        }

        return DiagnosticoGestacionMapper.toDTO(diagnostico);
    }

    private UUID obtenerIdHembraDeMonta(UUID idMonta) {
        Monta monta = montaRepository.findById(idMonta)
                .orElseThrow(() -> new IllegalArgumentException("La monta no existe"));
        return monta.getIdHembra();
    }

    public List<DiagnosticoGestacionResponseDTO> obtenerTodos() {
        return diagnosticoGestacionRepository.findAll()
                .stream()
                .map(DiagnosticoGestacionMapper::toDTO)
                .collect(Collectors.toList());
    }

    public DiagnosticoGestacionResponseDTO obtenerPorId(UUID id) {
        DiagnosticoGestacion diagnostico = diagnosticoGestacionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Diagnóstico no encontrado"));
        return DiagnosticoGestacionMapper.toDTO(diagnostico);
    }

    public List<DiagnosticoGestacionResponseDTO> obtenerPorMonta(UUID idMonta) {
        return diagnosticoGestacionRepository.findByIdMonta(idMonta)
                .stream()
                .map(DiagnosticoGestacionMapper::toDTO)
                .collect(Collectors.toList());
    }

    public DiagnosticoGestacionResponseDTO actualizar(UUID id, DiagnosticoGestacionRequest request) {
        DiagnosticoGestacion existente = diagnosticoGestacionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Diagnóstico no encontrado"));

        existente.setFecha(request.getFecha());
        existente.setResultado(request.getResultado());
        existente.setObservaciones(request.getObservaciones());

        return DiagnosticoGestacionMapper.toDTO(diagnosticoGestacionRepository.save(existente));
    }

    public void eliminar(UUID id) {
        diagnosticoGestacionRepository.deleteById(id);
    }
}
