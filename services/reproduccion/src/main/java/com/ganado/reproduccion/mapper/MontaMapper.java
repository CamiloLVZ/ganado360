package com.ganado.reproduccion.mapper;

import com.ganado.reproduccion.dto.MontaRequest;
import com.ganado.reproduccion.dto.MontaResponseDTO;
import com.ganado.reproduccion.model.Monta;

import java.util.List;
import java.util.stream.Collectors;

public class MontaMapper {

    // MontaRequest → Monta
    public static Monta toEntity(MontaRequest request) {
        return Monta.builder()
                //.id(UUID.randomUUID()) // Si quieres asignar manualmente el ID
                .idHembra(request.getIdHembra())
                .idMacho(request.getIdMacho())
                .fecha(request.getFecha())
                .metodoUtilizado(request.getMetodoUtilizado())
                .notas(request.getNotas())
                .estado(Monta.EstadoMonta.ACTIVA) // Siempre ACTIVA al crear
                .build();
    }

    // Monta → MontaResponseDTO
    public static MontaResponseDTO toDTO(Monta monta) {
        return MontaResponseDTO.builder()
                .id(monta.getId())
                .idHembra(monta.getIdHembra())
                .idMacho(monta.getIdMacho())
                .fecha(monta.getFecha())
                .metodoUtilizado(monta.getMetodoUtilizado())
                .notas(monta.getNotas())
                .estado(monta.getEstado())
                .build();
    }

    // Lista de Monta → Lista de MontaResponseDTO
    public static List<MontaResponseDTO> toDTOList(List<Monta> montas) {
        return montas.stream()
                .map(MontaMapper::toDTO)
                .collect(Collectors.toList());
    }
}