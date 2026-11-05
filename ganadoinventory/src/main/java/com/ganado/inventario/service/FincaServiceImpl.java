package com.ganado.inventario.service;

import com.ganado.inventario.dto.FincaRequestDTO;
import com.ganado.inventario.dto.FincaResponseDTO;
import com.ganado.inventario.model.Finca;
import com.ganado.inventario.repository.FincaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FincaServiceImpl implements FincaService {

    private final FincaRepository fincaRepository;

    @Override
    public FincaResponseDTO crear(FincaRequestDTO dto) {

        List<UUID> miembros = new ArrayList<>();

        // El creador siempre se agrega como miembro automáticamente
        miembros.add(dto.getUsuarioCreadorId());

        if (dto.getUsuarioMiembroIds() != null) {
            miembros.addAll(dto.getUsuarioMiembroIds());
        }

        Finca finca = Finca.builder()
                .nombre(dto.getNombre())
                .departamento(dto.getDepartamento())
                .municipio(dto.getMunicipio())
                .usuarioCreadorId(dto.getUsuarioCreadorId())
                .usuarioMiembroIds(miembros)
                .build();

        Finca guardada = fincaRepository.save(finca);

        return toResponse(guardada);
    }

    @Override
    public FincaResponseDTO obtener(UUID id) {
        return fincaRepository.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new RuntimeException("Finca no encontrada"));
    }

    @Override
    public List<FincaResponseDTO> listar() {
        return fincaRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public FincaResponseDTO actualizar(UUID id, FincaRequestDTO dto) {
        Finca finca = fincaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Finca no encontrada"));

        finca.setNombre(dto.getNombre());
        finca.setDepartamento(dto.getDepartamento());
        finca.setMunicipio(dto.getMunicipio());

        // La lógica de miembros se mantiene igual
        if (dto.getUsuarioMiembroIds() != null) {
            List<UUID> nuevas = new ArrayList<>();
            nuevas.add(finca.getUsuarioCreadorId());
            nuevas.addAll(dto.getUsuarioMiembroIds());
            finca.setUsuarioMiembroIds(nuevas);
        }

        return toResponse(fincaRepository.save(finca));
    }

    @Override
    public void eliminar(UUID id) {
        fincaRepository.deleteById(id);
    }

    private FincaResponseDTO toResponse(Finca finca) {
        return FincaResponseDTO.builder()
                .id(finca.getId())
                .nombre(finca.getNombre())
                .departamento(finca.getDepartamento())
                .municipio(finca.getMunicipio())
                .usuarioCreadorId(finca.getUsuarioCreadorId())
                .usuarioMiembroIds(finca.getUsuarioMiembroIds())
                .build();
    }
}