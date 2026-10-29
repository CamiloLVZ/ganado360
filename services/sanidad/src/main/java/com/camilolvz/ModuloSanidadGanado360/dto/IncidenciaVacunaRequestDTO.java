package com.camilolvz.ModuloSanidadGanado360.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;

import java.util.Date;
import java.util.UUID;

public class IncidenciaVacunaRequestDTO {

    /**
     * Id del producto (UUID).
     */
    @NotNull(message = "El id del producto es obligatorio")
    private UUID productoid;

    @NotNull(message = "El id del animal es obligatorio")
    private UUID idAnimal;

    @NotBlank(message = "El responsable es obligatorio")
    private String responsable;

    @NotNull(message = "La fecha de vacunación es obligatoria")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date fechaVacunacion;

    // Opcional: al crear asumimos PENDIENTE, pero permitimos recibirlo para actualizaciones
    private String estado;

    public IncidenciaVacunaRequestDTO() {}

    public UUID getProductoid() { return productoid; }
    public void setProductoid(UUID productoid) { this.productoid = productoid; }

    public UUID getIdAnimal() { return idAnimal; }
    public void setIdAnimal(UUID idAnimal) { this.idAnimal = idAnimal; }

    public String getResponsable() { return responsable; }
    public void setResponsable(String responsable) { this.responsable = responsable; }

    public Date getFechaVacunacion() { return fechaVacunacion; }
    public void setFechaVacunacion(Date fechaVacunacion) { this.fechaVacunacion = fechaVacunacion; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}
