package com.ganado.inventario.mapper;


import com.ganado.inventario.dto.AnimalRequestDTO;
import com.ganado.inventario.dto.AnimalResponseDTO;
import com.ganado.inventario.model.Animal;
import com.ganado.inventario.model.Finca;
import org.springframework.stereotype.Component;


@Component
public class AnimalMapper {

    public Animal toEntity(AnimalRequestDTO dto, Finca finca) {
        String estado = dto != null && dto.getEstado() != null ? dto.getEstado() : "Activo";
        return Animal.builder()
                .finca(finca)
                .especie(dto.getEspecie())
                .raza(dto.getRaza())
                .sexo(dto.getSexo())
                .fechaNacimiento(dto.getFechaNacimiento())
                .peso(dto.getPeso())
                .ubicacion(dto.getUbicacion())
                .estado(estado)
                .identificador(dto != null ? dto.getIdentificador() : null)
                .build();
    }

    public void updateEntity(Animal entity, AnimalRequestDTO dto, Finca finca) {
        entity.setFinca(finca); // por si cambia de finca
        entity.setEspecie(dto.getEspecie());
        entity.setRaza(dto.getRaza());
        entity.setSexo(dto.getSexo());
        entity.setFechaNacimiento(dto.getFechaNacimiento());
        entity.setPeso(dto.getPeso());
        entity.setUbicacion(dto.getUbicacion());

        // actualizamos estado solo si viene en el DTO
        if (dto.getEstado() != null) {
            entity.setEstado(dto.getEstado());
        }

        // actualizamos identificador solo si viene en el DTO
        if (dto.getIdentificador() != null) {
            entity.setIdentificador(dto.getIdentificador());
        }
    }

    public AnimalResponseDTO toResponse(Animal animal) {
        AnimalResponseDTO dto = new AnimalResponseDTO();
        dto.setId(animal.getId());
        dto.setFincaId(animal.getFinca().getId());
        dto.setEspecie(animal.getEspecie());
        dto.setRaza(animal.getRaza());
        dto.setSexo(animal.getSexo());
        dto.setFechaNacimiento(animal.getFechaNacimiento());
        dto.setPeso(animal.getPeso());
        dto.setUbicacion(animal.getUbicacion());
        dto.setEstado(animal.getEstado());
        dto.setIdentificador(animal.getIdentificador());
        return dto;
    }
}