package com.camilolvz.ModuloSanidadGanado360.model;

import jakarta.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
public class IncidenciaTratamiento {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "tratamiento_id")
    private TratamientoSanitario tratamiento;

    @Column(nullable = false)
    private UUID idAnimal;

    @Column(nullable = false)
    private String responsable;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date fechaTratamiento;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoIncidencia estado = EstadoIncidencia.PENDIENTE;

    // Nueva relación: muchas incidencias de tratamiento pueden pertenecer a UNA incidenciaEnfermedad
    @ManyToOne(optional = true)
    @JoinColumn(name = "incidencia_enfermedad_id")
    private IncidenciaEnfermedad incidenciaEnfermedad;

    public IncidenciaTratamiento() {}

    // ...constructores, getters y setters previos...

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public TratamientoSanitario getTratamiento() { return tratamiento; }
    public void setTratamiento(TratamientoSanitario tratamiento) { this.tratamiento = tratamiento; }

    public UUID getIdAnimal() { return idAnimal; }
    public void setIdAnimal(UUID idAnimal) { this.idAnimal = idAnimal; }

    public String getResponsable() { return responsable; }
    public void setResponsable(String responsable) { this.responsable = responsable; }

    public Date getFechaTratamiento() { return fechaTratamiento; }
    public void setFechaTratamiento(Date fechaTratamiento) { this.fechaTratamiento = fechaTratamiento; }

    public EstadoIncidencia getEstado() { return estado; }
    public void setEstado(EstadoIncidencia estado) { this.estado = estado; }

    public IncidenciaEnfermedad getIncidenciaEnfermedad() { return incidenciaEnfermedad; }
    public void setIncidenciaEnfermedad(IncidenciaEnfermedad incidenciaEnfermedad) { this.incidenciaEnfermedad = incidenciaEnfermedad; }
}
