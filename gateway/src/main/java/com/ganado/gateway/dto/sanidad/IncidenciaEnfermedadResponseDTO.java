package com.ganado.gateway.dto.sanidad;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * DTO de respuesta totalmente desacoplado de la capa de persistencia.
 */
public class IncidenciaEnfermedadResponseDTO {

    private UUID id;
    private UUID idAnimal;
    private UUID enfermedadId;
    private String enfermedadNombre;
    private String responsable;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date fechaDiagnostico;

    private List<UUID> tratamientoIds;

    private EstadoIncidenciaEnfermedad estado;

    public IncidenciaEnfermedadResponseDTO() {}

    public IncidenciaEnfermedadResponseDTO(UUID id,
                                           UUID idAnimal,
                                           UUID enfermedadId,
                                           String enfermedadNombre,
                                           String responsable,
                                           Date fechaDiagnostico,
                                           List<UUID> tratamientoIds,
                                           EstadoIncidenciaEnfermedad estado) {
        this.id = id;
        this.idAnimal = idAnimal;
        this.enfermedadId = enfermedadId;
        this.enfermedadNombre = enfermedadNombre;
        this.responsable = responsable;
        this.fechaDiagnostico = fechaDiagnostico;
        this.tratamientoIds = tratamientoIds;
        this.estado = estado;
    }

    // getters/setters, incluido getEstado/setEstado
    public EstadoIncidenciaEnfermedad getEstado() { return estado; }
    public void setEstado(EstadoIncidenciaEnfermedad estado) { this.estado = estado; }

    // Getters & setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public UUID getIdAnimal() { return idAnimal; }
    public void setIdAnimal(UUID idAnimal) { this.idAnimal = idAnimal; }

    public UUID getEnfermedadId() { return enfermedadId; }
    public void setEnfermedadId(UUID enfermedadId) { this.enfermedadId = enfermedadId; }

    public String getEnfermedadNombre() { return enfermedadNombre; }
    public void setEnfermedadNombre(String enfermedadNombre) { this.enfermedadNombre = enfermedadNombre; }

    public String getResponsable() { return responsable; }
    public void setResponsable(String responsable) { this.responsable = responsable; }

    public Date getFechaDiagnostico() { return fechaDiagnostico; }
    public void setFechaDiagnostico(Date fechaDiagnostico) { this.fechaDiagnostico = fechaDiagnostico; }

    public List<UUID> getTratamientoIds() { return tratamientoIds; }
    public void setTratamientoIds(List<UUID> tratamientoIds) { this.tratamientoIds = tratamientoIds; }
}
