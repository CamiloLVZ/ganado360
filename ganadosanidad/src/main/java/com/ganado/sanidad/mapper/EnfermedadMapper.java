package com.ganado.sanidad.mapper;

import com.ganado.sanidad.dto.EnfermedadRequestDTO;
import com.ganado.sanidad.dto.EnfermedadResponseDTO;
import com.ganado.sanidad.model.Enfermedad;
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
