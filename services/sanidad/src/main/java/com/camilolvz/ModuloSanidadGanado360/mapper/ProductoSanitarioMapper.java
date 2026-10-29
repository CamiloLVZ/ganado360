package com.camilolvz.ModuloSanidadGanado360.mapper;

import com.camilolvz.ModuloSanidadGanado360.dto.ProductoSanitarioRequestDTO;
import com.camilolvz.ModuloSanidadGanado360.dto.ProductoSanitarioResponseDTO;
import com.camilolvz.ModuloSanidadGanado360.model.ProductoSanitario;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ProductoSanitarioMapper {

    public ProductoSanitario toEntity(ProductoSanitarioRequestDTO req) {
        if (req == null) return null;
        ProductoSanitario p = new ProductoSanitario();
        p.setNombre(req.getNombre());
        p.setTipo(req.getTipo());
        p.setEspecies(req.getEspecies());
        return p;
    }

    public void updateEntityFromRequest(ProductoSanitario existing, ProductoSanitarioRequestDTO req) {
        if (existing == null || req == null) return;
        if (req.getNombre() != null && !req.getNombre().isBlank()) existing.setNombre(req.getNombre());
        if (req.getTipo() != null) existing.setTipo(req.getTipo());

        // Si envían especies (aunque sea lista vacía), la reemplazamos.
        // Si quieres comportamiento "merge" en lugar de replace, lo adaptamos.
        if (req.getEspecies() != null) {
            existing.setEspecies(req.getEspecies());
        }
    }

    public ProductoSanitarioResponseDTO toResponseDto(ProductoSanitario entity) {
        if (entity == null) return null;
        UUID id = entity.getId();
        return new ProductoSanitarioResponseDTO(
                id,
                entity.getNombre(),
                entity.getTipo(),
                entity.getEspecies()
        );
    }
}
