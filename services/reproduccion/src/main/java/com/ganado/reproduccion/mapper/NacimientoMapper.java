package com.ganado.reproduccion.mapper;

import com.ganado.reproduccion.dto.NacimientoRequestDTO;
import com.ganado.reproduccion.dto.NacimientoResponseDTO;
import com.ganado.reproduccion.model.Nacimiento;

public class NacimientoMapper {

    public static Nacimiento toEntity(NacimientoRequestDTO dto) {
        return Nacimiento.builder()
                .idMonta(dto.getIdMonta())
                .idMadre(dto.getIdMadre())
                .idAnimal(dto.getIdAnimal())
                .fecha(dto.getFecha())
                .sexo(dto.getSexo())
                .peso(dto.getPeso())
                .observaciones(dto.getObservaciones())
                .build();
    }

    public static NacimientoResponseDTO toDTO(Nacimiento entity) {
        return NacimientoResponseDTO.builder()
                .id(entity.getId())
                .idMonta(entity.getIdMonta())
                .idMadre(entity.getIdMadre())
                .idAnimal(entity.getIdAnimal())
                .fecha(entity.getFecha())
                .sexo(entity.getSexo())
                .peso(entity.getPeso())
                .observaciones(entity.getObservaciones())
                .build();
    }
}
