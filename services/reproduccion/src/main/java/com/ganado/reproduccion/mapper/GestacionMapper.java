package com.ganado.reproduccion.mapper;

import com.ganado.reproduccion.dto.GestacionRequest;
import com.ganado.reproduccion.dto.GestacionResponseDTO;
import com.ganado.reproduccion.model.Gestacion;

public class GestacionMapper {

    // Convertir Request → Entity
    public static Gestacion toEntity(GestacionRequest request) {
        return Gestacion.builder()
                .idMonta(request.getIdMonta())
                .idHembra(request.getIdHembra())
                .fechaInicio(request.getFechaInicio())
                .fechaEstimadaParto(request.getFechaEstimadaParto())
                .estado(request.getEstado() != null
                        ? Gestacion.EstadoGestacion.valueOf(request.getEstado().toUpperCase())
                        : Gestacion.EstadoGestacion.ACTIVA)
                .build();
    }

    // Convertir Entity → ResponseDTO
    public static GestacionResponseDTO toDTO(Gestacion gestacion) {
        return GestacionResponseDTO.builder()
                .id(gestacion.getId())
                .idMonta(gestacion.getIdMonta())
                .idHembra(gestacion.getIdHembra())
                .fechaInicio(gestacion.getFechaInicio())
                .fechaEstimadaParto(gestacion.getFechaEstimadaParto())
                .estado(gestacion.getEstado())
                .build();
    }
}


