package com.ganado.inventario.service;

import com.ganado.inventario.dto.AnimalHistoryRequestDTO;
import com.ganado.inventario.dto.AnimalHistoryResponseDTO;

import java.util.List;
import java.util.UUID;

public interface AnimalHistoryService {
    AnimalHistoryResponseDTO crear(AnimalHistoryRequestDTO dto);
    AnimalHistoryResponseDTO obtenerPorId(UUID id);
    List<AnimalHistoryResponseDTO> listarTodos();
    List<AnimalHistoryResponseDTO> listarPorAnimal(UUID animalId);
    AnimalHistoryResponseDTO actualizar(UUID id, AnimalHistoryRequestDTO dto);
    void eliminar(UUID id);

    byte[] exportarHistorialAnimalPdf(UUID animalId);
}
