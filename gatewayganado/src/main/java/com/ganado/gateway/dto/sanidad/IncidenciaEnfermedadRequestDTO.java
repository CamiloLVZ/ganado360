package com.ganado.gateway.dto.sanidad;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class IncidenciaEnfermedadRequestDTO {

    @NotNull(message = "enfermedadId es obligatorio")
    private UUID enfermedadId;

    @NotNull(message = "idAnimal es obligatorio")
    private UUID idAnimal;

    @NotBlank(message = "responsable es obligatorio")
    private String responsable;

    @NotNull(message = "fechaDiagnostico es obligatoria")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date fechaDiagnostico;

    private List<UUID> tratamientoIds;

    // opcional: permitir enviar estado (si no viene, se usa DIAGNOSTICADA)
    private EstadoIncidenciaEnfermedad estado;


    // getters y setters inclusive para estado
    public EstadoIncidenciaEnfermedad getEstado() { return estado; }
    public void setEstado(EstadoIncidenciaEnfermedad estado) { this.estado = estado; }

    public IncidenciaEnfermedadRequestDTO() {}

    public UUID getEnfermedadId() { return enfermedadId; }
    public void setEnfermedadId(UUID enfermedadId) { this.enfermedadId = enfermedadId; }

    public UUID getIdAnimal() { return idAnimal; }
    public void setIdAnimal(UUID idAnimal) { this.idAnimal = idAnimal; }

    public String getResponsable() { return responsable; }
    public void setResponsable(String responsable) { this.responsable = responsable; }

    public Date getFechaDiagnostico() { return fechaDiagnostico; }
    public void setFechaDiagnostico(Date fechaDiagnostico) { this.fechaDiagnostico = fechaDiagnostico; }

    public List<UUID> getTratamientoIds() { return tratamientoIds; }
    public void setTratamientoIds(List<UUID> tratamientoIds) { this.tratamientoIds = tratamientoIds; }
}
