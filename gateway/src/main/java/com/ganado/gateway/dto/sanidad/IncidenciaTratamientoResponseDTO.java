package com.ganado.gateway.dto.sanidad;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.util.UUID;

public class IncidenciaTratamientoResponseDTO {

    private UUID id;
    private UUID idAnimal;
    private UUID tratamientoId;
    private String tratamientoNombre;
    private String responsable;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date fechaTratamiento;

    private EstadoIncidencia estado;

    public IncidenciaTratamientoResponseDTO() {}

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public UUID getIdAnimal() { return idAnimal; }
    public void setIdAnimal(UUID idAnimal) { this.idAnimal = idAnimal; }

    public UUID getTratamientoId() { return tratamientoId; }
    public void setTratamientoId(UUID tratamientoId) { this.tratamientoId = tratamientoId; }

    public String getTratamientoNombre() { return tratamientoNombre; }
    public void setTratamientoNombre(String tratamientoNombre) { this.tratamientoNombre = tratamientoNombre; }

    public String getResponsable() { return responsable; }
    public void setResponsable(String responsable) { this.responsable = responsable; }

    public Date getFechaTratamiento() { return fechaTratamiento; }
    public void setFechaTratamiento(Date fechaTratamiento) { this.fechaTratamiento = fechaTratamiento; }

    public EstadoIncidencia getEstado() { return estado; }
    public void setEstado(EstadoIncidencia estado) { this.estado = estado; }
}
