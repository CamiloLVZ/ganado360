package com.camilolvz.ModuloSanidadGanado360.mapper;

import com.camilolvz.ModuloSanidadGanado360.dto.TiempoUnidadDTO;
import com.camilolvz.ModuloSanidadGanado360.dto.TratamientoRequestDTO;
import com.camilolvz.ModuloSanidadGanado360.dto.TratamientoResponseDTO;
import com.camilolvz.ModuloSanidadGanado360.model.TiempoUnidad;
import com.camilolvz.ModuloSanidadGanado360.model.TratamientoSanitario;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

/**
 * Mapper para TratamientoSanitario <-> DTOs.
 * Toda la conversión y validación de unidades se centraliza aquí.
 */
@Component
public class TratamientoMapper {

    /**
     * Convierte RequestDTO a entidad nueva (sin id).
     * Valida y convierte unidades String -> model.TiempoUnidad.
     */
    public TratamientoSanitario toEntity(TratamientoRequestDTO req) {
        if (req == null) return null;
        TratamientoSanitario t = new TratamientoSanitario();
        t.setNombre(req.getNombre());
        t.setDescripcion(req.getDescripcion());
        t.setMedicamento(req.getMedicamento());
        t.setDosis(req.getDosis());

        if (req.getDuracionTotalCantidad() != null) t.setDuracionTotalCantidad(req.getDuracionTotalCantidad());
        if (req.getDuracionTotalUnidad() != null && !req.getDuracionTotalUnidad().isBlank()) {
            t.setDuracionTotalUnidad(parseUnidad(req.getDuracionTotalUnidad()));
        }

        if (req.getIntervaloCantidad() != null) t.setIntervaloCantidad(req.getIntervaloCantidad());
        if (req.getIntervaloUnidad() != null && !req.getIntervaloUnidad().isBlank()) {
            t.setIntervaloUnidad(parseUnidad(req.getIntervaloUnidad()));
        }

        return t;
    }

    /**
     * Convierte entidad a ResponseDTO (DTO puro).
     */
    public TratamientoResponseDTO toDto(TratamientoSanitario e) {
        if (e == null) return null;

        TiempoUnidadDTO durUnidadDto = null;
        if (e.getDuracionTotalUnidad() != null) {
            try {
                durUnidadDto = TiempoUnidadDTO.valueOf(e.getDuracionTotalUnidad().name());
            } catch (IllegalArgumentException ignored) {}
        }

        TiempoUnidadDTO intUnidadDto = null;
        if (e.getIntervaloUnidad() != null) {
            try {
                intUnidadDto = TiempoUnidadDTO.valueOf(e.getIntervaloUnidad().name());
            } catch (IllegalArgumentException ignored) {}
        }

        return new TratamientoResponseDTO(
                e.getId(),
                e.getNombre(),
                e.getDescripcion(),
                e.getMedicamento(),
                e.getDosis(),
                e.getDuracionTotalCantidad(),
                durUnidadDto,
                e.getIntervaloCantidad(),
                intUnidadDto
        );
    }

    /**
     * Actualiza entidad existente con campos del RequestDTO (parcial).
     * Valida unidades y lanza ResponseStatusException si son inválidas.
     */
    public void updateEntityFromRequest(TratamientoSanitario existing, TratamientoRequestDTO req) {
        if (existing == null || req == null) return;

        if (req.getNombre() != null && !req.getNombre().isBlank()) existing.setNombre(req.getNombre());
        if (req.getDescripcion() != null) existing.setDescripcion(req.getDescripcion());
        if (req.getMedicamento() != null) existing.setMedicamento(req.getMedicamento());
        if (req.getDosis() != null) existing.setDosis(req.getDosis());

        if (req.getDuracionTotalCantidad() != null) existing.setDuracionTotalCantidad(req.getDuracionTotalCantidad());
        if (req.getDuracionTotalUnidad() != null && !req.getDuracionTotalUnidad().isBlank()) {
            existing.setDuracionTotalUnidad(parseUnidad(req.getDuracionTotalUnidad()));
        }

        if (req.getIntervaloCantidad() != null) existing.setIntervaloCantidad(req.getIntervaloCantidad());
        if (req.getIntervaloUnidad() != null && !req.getIntervaloUnidad().isBlank()) {
            existing.setIntervaloUnidad(parseUnidad(req.getIntervaloUnidad()));
        }
    }

    /**
     * Convierte String -> model.TiempoUnidad (case-insensitive).
     * Lanza ResponseStatusException(HttpStatus.BAD_REQUEST) si inválida.
     */
    private TiempoUnidad parseUnidad(String unidad) {
        try {
            return TiempoUnidad.valueOf(unidad.toUpperCase());
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unidad inválida: " + unidad);
        }
    }
}
