package com.ganado.inventario.mapper;

import com.ganado.inventario.dto.AnimalHistoryRequestDTO;
import com.ganado.inventario.dto.AnimalHistoryResponseDTO;
import com.ganado.inventario.model.Animal;
import com.ganado.inventario.model.AnimalHistory;
import org.springframework.stereotype.Component;

@Component
public class AnimalHistoryMapper {

    public AnimalHistory toEntity(AnimalHistoryRequestDTO dto, Animal animal) {
        return AnimalHistory.builder()
                .animal(animal)
                .descripcion(dto.getDescripcion())
                .fechaCreacion(dto.getFechaCreacion()) // puede ser null -> @PrePersist setea
                // diaCreacion lo deja null para que @PrePersist lo calcule
                .build();
    }

    public void updateEntity(AnimalHistory entity, AnimalHistoryRequestDTO dto, Animal animal) {
        // No permitimos cambiar el animal por defecto, pero si quieres permitirlo, descomenta:
        // entity.setAnimal(animal);
        entity.setDescripcion(dto.getDescripcion());

        if (dto.getFechaCreacion() != null) {
            entity.setFechaCreacion(dto.getFechaCreacion());
            // recalcular día en español inmediatamente
            String dia = dto.getFechaCreacion()
                    .getDayOfWeek()
                    .getDisplayName(java.time.format.TextStyle.FULL, new java.util.Locale("es", "ES"))
                    .toLowerCase();
            entity.setDiaCreacion(dia);
        }
    }

    public AnimalHistoryResponseDTO toResponse(AnimalHistory e) {
        AnimalHistoryResponseDTO dto = new AnimalHistoryResponseDTO();
        dto.setId(e.getId());
        dto.setAnimalId(e.getAnimal().getId());
        dto.setDescripcion(e.getDescripcion());
        dto.setFechaCreacion(e.getFechaCreacion());
        dto.setDiaCreacion(e.getDiaCreacion());
        return dto;
    }
}
