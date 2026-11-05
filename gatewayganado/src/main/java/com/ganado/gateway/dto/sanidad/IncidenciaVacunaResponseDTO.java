package com.ganado.gateway.dto.sanidad;


import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.util.UUID;

/**
 * DTO de respuesta totalmente desacoplado de la capa model.
 */
public class IncidenciaVacunaResponseDTO {

    private UUID id;
    private UUID idAnimal;
    private UUID productoId;
    private String productoNombre;
    private String responsable;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date fechaVacunacion;

    private EstadoIncidenciaDTO estado;

    public IncidenciaVacunaResponseDTO() {}

    public IncidenciaVacunaResponseDTO(UUID id, UUID idAnimal, UUID productoId, String productoNombre, String responsable, Date fechaVacunacion, EstadoIncidenciaDTO estado) {
        this.id = id;
        this.idAnimal = idAnimal;
        this.productoId = productoId;
        this.productoNombre = productoNombre;
        this.responsable = responsable;
        this.fechaVacunacion = fechaVacunacion;
        this.estado = estado;
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public UUID getIdAnimal() { return idAnimal; }
    public void setIdAnimal(UUID idAnimal) { this.idAnimal = idAnimal; }

    public UUID getProductoId() { return productoId; }
    public void setProductoId(UUID productoId) { this.productoId = productoId; }

    public String getProductoNombre() { return productoNombre; }
    public void setProductoNombre(String productoNombre) { this.productoNombre = productoNombre; }

    public String getResponsable() { return responsable; }
    public void setResponsable(String responsable) { this.responsable = responsable; }

    public Date getFechaVacunacion() { return fechaVacunacion; }
    public void setFechaVacunacion(Date fechaVacunacion) { this.fechaVacunacion = fechaVacunacion; }

    public EstadoIncidenciaDTO getEstado() { return estado; }
    public void setEstado(EstadoIncidenciaDTO estado) { this.estado = estado; }
}
