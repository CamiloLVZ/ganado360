package com.ganado.gateway.dto.sanidad;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public class RefuerzoRequestDTO {

    @NotNull
    private UUID productoId; // antes: enfermedadId

    @NotNull @Min(1)
    private Integer cantidad;

    @NotNull
    private String unidad; // "DIAS"|"SEMANAS"|"MESES"|"ANOS"

    public RefuerzoRequestDTO() {}

    public UUID getProductoId() { return productoId; }
    public void setProductoId(UUID productoId) { this.productoId = productoId; }

    public Integer getCantidad() { return cantidad; }
    public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }

    public String getUnidad() { return unidad; }
    public void setUnidad(String unidad) { this.unidad = unidad; }
}
