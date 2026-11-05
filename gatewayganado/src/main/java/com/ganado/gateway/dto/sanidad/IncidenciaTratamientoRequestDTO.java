package com.ganado.gateway.dto.sanidad;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Date;
import java.util.UUID;

public class IncidenciaTratamientoRequestDTO {

    @NotNull(message = "tratamientoId es obligatorio")
    private UUID tratamientoId;

    @NotNull(message = "idAnimal es obligatorio")
    private UUID idAnimal;

    @NotBlank(message = "responsable es obligatorio")
    private String responsable;

    @NotNull(message = "fechaTratamiento es obligatoria")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date fechaTratamiento;

    // Opcional — se seteará por defecto a PENDIENTE si no se envía
    private String estado;

    // NUEVO: Id de la IncidenciaEnfermedad (opcional). Si se envía, la incidencia tratamiento se vinculará.
    private UUID incidenciaEnfermedadId;

    public IncidenciaTratamientoRequestDTO() {}

    public UUID getTratamientoId() { return tratamientoId; }
    public void setTratamientoId(UUID tratamientoId) { this.tratamientoId = tratamientoId; }

    public UUID getIdAnimal() { return idAnimal; }
    public void setIdAnimal(UUID idAnimal) { this.idAnimal = idAnimal; }

    public String getResponsable() { return responsable; }
    public void setResponsable(String responsable) { this.responsable = responsable; }

    public Date getFechaTratamiento() { return fechaTratamiento; }
    public void setFechaTratamiento(Date fechaTratamiento) { this.fechaTratamiento = fechaTratamiento; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public UUID getIncidenciaEnfermedadId() { return incidenciaEnfermedadId; }
    public void setIncidenciaEnfermedadId(UUID incidenciaEnfermedadId) { this.incidenciaEnfermedadId = incidenciaEnfermedadId; }
}
