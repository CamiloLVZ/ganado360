package com.camilolvz.ModuloSanidadGanado360.mapper;


import com.camilolvz.ModuloSanidadGanado360.dto.RefuerzoRequestDTO;
import com.camilolvz.ModuloSanidadGanado360.dto.RefuerzoResponseDTO;
import com.camilolvz.ModuloSanidadGanado360.model.Refuerzo;
import com.camilolvz.ModuloSanidadGanado360.model.ProductoSanitario;
import com.camilolvz.ModuloSanidadGanado360.model.TiempoUnidad;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

/**
 * Mapper centralizado para Refuerzo.
 * - Convierte RequestDTO + ProductoSanitario -> Entidad
 * - Convierte Entidad -> ResponseDTO (puro)
 * - Actualiza entidad existente desde RequestDTO
 */
@Component
public class RefuerzoMapper {

    /**
     * Convierte RequestDTO + producto resuelto a entidad nueva.
     * Lanza ResponseStatusException(HttpStatus.BAD_REQUEST) si la unidad es inválida.
     */
    public Refuerzo toEntity(RefuerzoRequestDTO req, ProductoSanitario producto) {
        if (req == null) return null;
        if (producto == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Producto inválido o no encontrado");
        }

        TiempoUnidad unidad;
        try {
            unidad = TiempoUnidad.valueOf(req.getUnidad().toUpperCase());
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unidad inválida: " + req.getUnidad());
        }

        Refuerzo r = new Refuerzo();
        r.setProducto(producto);
        r.setCantidad(req.getCantidad());
        r.setUnidad(unidad);
        return r;
    }

    /**
     * Convierte entidad a DTO independiente.
     */
    public RefuerzoResponseDTO toDto(Refuerzo r) {
        if (r == null) return null;

        UUID productoId = null;
        String productoNombre = null;
        if (r.getProducto() != null) {
            productoId = r.getProducto().getId();
            productoNombre = r.getProducto().getNombre();
        }

        String unidadStr = r.getUnidad() != null ? r.getUnidad().name() : null;

        return new RefuerzoResponseDTO(
                r.getId(),
                productoId,
                productoNombre,
                r.getCantidad(),
                unidadStr
        );
    }

    /**
     * Actualiza entidad existente con datos del RequestDTO (parcial).
     * - Si 'producto' es non-null lo asigna; si es null no toca el producto.
     * - Valida y convierte la unidad; si inválida lanza ResponseStatusException.
     */
    public void updateEntityFromDto(Refuerzo existente, RefuerzoRequestDTO req, ProductoSanitario producto) {
        if (existente == null || req == null) return;

        if (producto != null) {
            existente.setProducto(producto);
        }

        if (req.getCantidad() != null) existente.setCantidad(req.getCantidad());

        if (req.getUnidad() != null) {
            try {
                TiempoUnidad unidad = TiempoUnidad.valueOf(req.getUnidad().toUpperCase());
                existente.setUnidad(unidad);
            } catch (Exception ex) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unidad inválida: " + req.getUnidad());
            }
        }
    }
}
