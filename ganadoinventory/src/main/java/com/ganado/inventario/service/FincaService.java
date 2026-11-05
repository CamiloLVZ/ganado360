package com.ganado.inventario.service;

import com.ganado.inventario.dto.FincaRequestDTO;
import com.ganado.inventario.dto.FincaResponseDTO;

import java.util.List;
import java.util.UUID;

public interface FincaService {
    FincaResponseDTO crear(FincaRequestDTO dto);
    FincaResponseDTO obtener(UUID id);
    List<FincaResponseDTO> listar();
    FincaResponseDTO actualizar(UUID id, FincaRequestDTO dto);
    void eliminar(UUID id);
}
