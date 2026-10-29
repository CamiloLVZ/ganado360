package com.ganado.reproduccion.mapper;

import com.ganado.reproduccion.dto.DiagnosticoGestacionRequest;
import com.ganado.reproduccion.dto.DiagnosticoGestacionResponseDTO;
import com.ganado.reproduccion.model.DiagnosticoGestacion;
import com.ganado.reproduccion.model.Gestacion;
import lombok.experimental.UtilityClass;

import java.time.LocalDate;
import java.util.UUID;

@UtilityClass
public class DiagnosticoGestacionMapper {

    public DiagnosticoGestacion toEntity(DiagnosticoGestacionRequest request) {
        return DiagnosticoGestacion.builder()
                .idMonta(request.getIdMonta())
                .fecha(request.getFecha())
                .resultado(request.getResultado())
                .observaciones(request.getObservaciones())
                .build();
    }

    public Gestacion toGestacion(DiagnosticoGestacionRequest request, UUID idHembra) {
        LocalDate fechaEstimadaParto = request.getFecha().plusDays(obtenerDiasGestacionPorEspecie(request.getEspecie()));
        return Gestacion.builder()
                .idMonta(request.getIdMonta())
                .idHembra(idHembra)
                .fechaInicio(request.getFecha())
                .fechaEstimadaParto(fechaEstimadaParto)
                .estado(Gestacion.EstadoGestacion.ACTIVA)
                .build();
    }

    public DiagnosticoGestacionResponseDTO toDTO(DiagnosticoGestacion diagnostico) {
        return DiagnosticoGestacionResponseDTO.builder()
                .id(diagnostico.getId())
                .idMonta(diagnostico.getIdMonta())
                .fecha(diagnostico.getFecha())
                .resultado(diagnostico.getResultado())
                .observaciones(diagnostico.getObservaciones())
                .build();
    }

    private int obtenerDiasGestacionPorEspecie(String especie) {
        return switch (especie.toUpperCase()) {
            case "BOVINO" -> 283;
            case "OVINO", "CAPRINO" -> 150;
            case "EQUINO" -> 336;
            case "PORCINO" -> 115;
            case "AVE DE CORRAL" -> 21;
            default -> 280;
        };
    }
}
