package com.ganado.gateway.dto.sanidad;

import java.util.UUID;

/**
 * DTO de respuesta independiente — no importa entidades ni enums del model.
 */
public class RefuerzoResponseDTO {

    private UUID id;
    private UUID productoId;
    private String productoNombre;
    private Integer cantidad;
    private String unidad; // "DIAS" | "SEMANAS" | "MESES" | "ANOS"

    public RefuerzoResponseDTO() {}

    public RefuerzoResponseDTO(UUID id, UUID productoId, String productoNombre, Integer cantidad, String unidad) {
        this.id = id;
        this.productoId = productoId;
        this.productoNombre = productoNombre;
        this.cantidad = cantidad;
        this.unidad = unidad;
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public UUID getProductoId() { return productoId; }
    public void setProductoId(UUID productoId) { this.productoId = productoId; }

    public String getProductoNombre() { return productoNombre; }
    public void setProductoNombre(String productoNombre) { this.productoNombre = productoNombre; }

    public Integer getCantidad() { return cantidad; }
    public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }

    public String getUnidad() { return unidad; }
    public void setUnidad(String unidad) { this.unidad = unidad; }
}
