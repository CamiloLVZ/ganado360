package com.ganado.reproduccion.mapper;


import com.ganado.reproduccion.dto.GenealogiaRequestDTO;
import com.ganado.reproduccion.dto.GenealogiaResponseDTO;
import com.ganado.reproduccion.model.Genealogia;
import org.springframework.stereotype.Component;

@Component
public class GenealogiaMapper {

    public Genealogia toEntity(GenealogiaRequestDTO dto) {
        if (dto == null) return null;
        return Genealogia.builder()
                .hijo(dto.getHijo())
                .madre(dto.getMadre())
                .padre(dto.getPadre())
                .build();
    }

    public GenealogiaResponseDTO toResponse(Genealogia entity) {
        if (entity == null) return null;
        return GenealogiaResponseDTO.builder()
                .hijo(entity.getHijo())
                .madre(entity.getMadre())
                .padre(entity.getPadre())
                .build();
    }

    public void updateEntityFromDto(GenealogiaRequestDTO dto, Genealogia entity) {
        if (dto == null || entity == null) return;
        // Hijo se usa como id, normalmente no se cambia; si quieres permitir cambiarlo,
        // habría que tratarlo con cuidado. Aquí no tocamos el id si coincide.
        entity.setMadre(dto.getMadre());
        entity.setPadre(dto.getPadre());
    }
}

