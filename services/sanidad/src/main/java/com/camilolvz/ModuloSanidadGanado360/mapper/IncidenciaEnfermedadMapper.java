package com.camilolvz.ModuloSanidadGanado360.mapper;

import com.camilolvz.ModuloSanidadGanado360.dto.IncidenciaEnfermedadRequestDTO;
import com.camilolvz.ModuloSanidadGanado360.dto.IncidenciaEnfermedadResponseDTO;
import com.camilolvz.ModuloSanidadGanado360.model.Enfermedad;
import com.camilolvz.ModuloSanidadGanado360.model.IncidenciaEnfermedad;
import com.camilolvz.ModuloSanidadGanado360.model.IncidenciaTratamiento;
import com.camilolvz.ModuloSanidadGanado360.model.EstadoIncidenciaEnfermedad;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class IncidenciaEnfermedadMapper {

    public IncidenciaEnfermedad toEntity(IncidenciaEnfermedadRequestDTO dto, Enfermedad enf) {
        if (dto == null) return null;
        IncidenciaEnfermedad e = new IncidenciaEnfermedad();
        e.setEnfermedad(enf);
        e.setIdAnimal(dto.getIdAnimal());
        e.setResponsable(dto.getResponsable());
        e.setFechaDiagnostico(dto.getFechaDiagnostico());
        // si viene estado en el DTO lo usamos, si no, el modelo ya tiene DIAGNOSTICADA por defecto
        if (dto.getEstado() != null) {
            e.setEstado(dto.getEstado());
        }
        return e;
    }

    public IncidenciaEnfermedadResponseDTO toDto(IncidenciaEnfermedad entity) {
        if (entity == null) return null;
        List<java.util.UUID> tratamientoIds = entity.getTratamientos() == null ? List.of()
                : entity.getTratamientos().stream().map(IncidenciaTratamiento::getId).collect(Collectors.toList());

        return new IncidenciaEnfermedadResponseDTO(
                entity.getId(),
                entity.getIdAnimal(),
                entity.getEnfermedad().getId(),
                entity.getEnfermedad().getNombre(),
                entity.getResponsable(),
                entity.getFechaDiagnostico(),
                tratamientoIds,
                entity.getEstado()
        );
    }

    public void updateEntityFromDto(IncidenciaEnfermedadRequestDTO dto, IncidenciaEnfermedad existente, Enfermedad enf) {
        if (dto == null || existente == null) return;
        if (dto.getIdAnimal() != null) existente.setIdAnimal(dto.getIdAnimal());
        if (dto.getResponsable() != null) existente.setResponsable(dto.getResponsable());
        if (dto.getFechaDiagnostico() != null) existente.setFechaDiagnostico(dto.getFechaDiagnostico());
        if (enf != null) existente.setEnfermedad(enf);
        if (dto.getEstado() != null) existente.setEstado(dto.getEstado());
    }
}
