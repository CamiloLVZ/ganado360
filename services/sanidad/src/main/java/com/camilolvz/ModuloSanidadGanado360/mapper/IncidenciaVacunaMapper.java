package com.camilolvz.ModuloSanidadGanado360.mapper;


import com.camilolvz.ModuloSanidadGanado360.dto.EstadoIncidenciaDTO;
import com.camilolvz.ModuloSanidadGanado360.dto.IncidenciaVacunaRequestDTO;
import com.camilolvz.ModuloSanidadGanado360.dto.IncidenciaVacunaResponseDTO;
import com.camilolvz.ModuloSanidadGanado360.model.IncidenciaVacuna;
import com.camilolvz.ModuloSanidadGanado360.model.ProductoSanitario;
import com.camilolvz.ModuloSanidadGanado360.model.EstadoIncidencia;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

/**
 * Mapper centralizado: convierte entidad <-> DTO (Request/Response).
 * TODO: si prefieres una clase estática para librería compartida, lo convertimos fácilmente.
 */
@Component
public class IncidenciaVacunaMapper {

    /**
     * Convierte RequestDTO + ProductoSanitario resuelto a entidad nueva.
     * - El servicio debe resolver/obtener el ProductoSanitario y pasarlo aquí.
     */
    public IncidenciaVacuna toEntity(IncidenciaVacunaRequestDTO req, ProductoSanitario producto) {
        if (req == null) return null;
        IncidenciaVacuna e = new IncidenciaVacuna();
        e.setProductoUsado(producto);
        e.setIdAnimal(req.getIdAnimal());
        e.setResponsable(req.getResponsable());
        e.setFechaVacunacion(req.getFechaVacunacion());

        // estado opcional
        if (req.getEstado() != null && !req.getEstado().isBlank()) {
            try {
                EstadoIncidencia estado = EstadoIncidencia.valueOf(req.getEstado().toUpperCase());
                e.setEstado(estado);
            } catch (IllegalArgumentException ex) {
                // dejar que el servicio valide y lance error si lo desea
            }
        }
        return e;
    }

    /**
     * Convierte entidad a ResponseDTO (dto independiente).
     */
    public IncidenciaVacunaResponseDTO toDto(IncidenciaVacuna e) {
        if (e == null) return null;

        EstadoIncidenciaDTO estadoDto = null;
        if (e.getEstado() != null) {
            try {
                estadoDto = EstadoIncidenciaDTO.valueOf(e.getEstado().name());
            } catch (IllegalArgumentException ignored) { }
        }

        UUID productoId = null;
        String productoNombre = null;
        if (e.getProductoUsado() != null) {
            productoId = e.getProductoUsado().getId();
            productoNombre = e.getProductoUsado().getNombre();
        }

        return new IncidenciaVacunaResponseDTO(
                e.getId(),
                e.getIdAnimal(),
                productoId,
                productoNombre,
                e.getResponsable(),
                e.getFechaVacunacion(),
                estadoDto
        );
    }

    /**
     * Actualiza una entidad existente con datos parciales del DTO.
     * - Si 'producto' es non-null lo asigna; en caso contrario no toca el producto.
     */
    public void updateEntityFromDto(IncidenciaVacuna existente, IncidenciaVacunaRequestDTO req, ProductoSanitario producto) {
        if (existente == null || req == null) return;

        if (producto != null) existente.setProductoUsado(producto);
        if (req.getIdAnimal() != null) existente.setIdAnimal(req.getIdAnimal());
        if (req.getResponsable() != null && !req.getResponsable().isBlank()) existente.setResponsable(req.getResponsable());
        if (req.getFechaVacunacion() != null) existente.setFechaVacunacion(req.getFechaVacunacion());

        if (req.getEstado() != null && !req.getEstado().isBlank()) {
            try {
                EstadoIncidencia nuevo = EstadoIncidencia.valueOf(req.getEstado().toUpperCase());
                existente.setEstado(nuevo);
            } catch (IllegalArgumentException ignored) { }
        }
    }
}

