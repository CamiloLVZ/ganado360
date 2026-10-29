package com.camilolvz.ModuloSanidadGanado360.mapper;

import com.camilolvz.ModuloSanidadGanado360.dto.EnfermedadRequestDTO;
import com.camilolvz.ModuloSanidadGanado360.dto.EnfermedadResponseDTO;
import com.camilolvz.ModuloSanidadGanado360.model.Enfermedad;
import org.springframework.stereotype.Component;

@Component
public class EnfermedadMapper {

    public Enfermedad toEntity(EnfermedadRequestDTO dto) {
        Enfermedad e = new Enfermedad();
        e.setNombre(dto.getNombre());
        e.setDescripcion(dto.getDescripcion());
        return e;
    }

    public EnfermedadResponseDTO toDTO(Enfermedad e) {
        return new EnfermedadResponseDTO(
                e.getId(),
                e.getNombre(),
                e.getDescripcion()
        );
    }
}
