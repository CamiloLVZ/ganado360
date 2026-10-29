package com.ganado.inventario.service;

import com.ganado.inventario.dto.AnimalRequestDTO;
import com.ganado.inventario.dto.AnimalResponseDTO;


import java.util.List;
import java.util.UUID;


public interface AnimalService {

    AnimalResponseDTO crear(AnimalRequestDTO dto);

    AnimalResponseDTO obtenerPorId(UUID id);

    List<AnimalResponseDTO> listarTodos();

    AnimalResponseDTO actualizar(UUID id, AnimalRequestDTO dto);

    void eliminar(UUID id);

    List<AnimalResponseDTO> obtenerPorEspecie(String especie);
}