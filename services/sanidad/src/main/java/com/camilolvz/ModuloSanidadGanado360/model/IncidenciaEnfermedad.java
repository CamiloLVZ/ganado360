package com.camilolvz.ModuloSanidadGanado360.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
public class IncidenciaEnfermedad {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    // UUID del animal (microservicio externo)
    @Column(nullable = false)
    private UUID idAnimal;

    @Column(nullable = false)
    private String responsable;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date fechaDiagnostico;

    // Relación con Enfermedad (misma base de datos)
    @ManyToOne(optional = false)
    @JoinColumn(name = "enfermedad_id")
    private Enfermedad enfermedad;

    // Estado nuevo
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoIncidenciaEnfermedad estado = EstadoIncidenciaEnfermedad.DIAGNOSTICADA;

    // Una incidenciaEnfermedad puede tener muchas incidenciaTratamiento
    @OneToMany(mappedBy = "incidenciaEnfermedad",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            orphanRemoval = false,
            fetch = FetchType.LAZY)
    private List<IncidenciaTratamiento> tratamientos = new ArrayList<>();

    public IncidenciaEnfermedad() {}

    public IncidenciaEnfermedad(UUID idAnimal, String responsable, Date fechaDiagnostico, Enfermedad enfermedad) {
        this.idAnimal = idAnimal;
        this.responsable = responsable;
        this.fechaDiagnostico = fechaDiagnostico;
        this.enfermedad = enfermedad;
        this.estado = EstadoIncidenciaEnfermedad.DIAGNOSTICADA;
    }

    // getters / setters...

    public EstadoIncidenciaEnfermedad getEstado() { return estado; }
    public void setEstado(EstadoIncidenciaEnfermedad estado) { this.estado = estado; }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public UUID getIdAnimal() { return idAnimal; }
    public void setIdAnimal(UUID idAnimal) { this.idAnimal = idAnimal; }

    public String getResponsable() { return responsable; }
    public void setResponsable(String responsable) { this.responsable = responsable; }

    public Date getFechaDiagnostico() { return fechaDiagnostico; }
    public void setFechaDiagnostico(Date fechaDiagnostico) { this.fechaDiagnostico = fechaDiagnostico; }

    public Enfermedad getEnfermedad() { return enfermedad; }
    public void setEnfermedad(Enfermedad enfermedad) { this.enfermedad = enfermedad; }

    public List<IncidenciaTratamiento> getTratamientos() { return tratamientos; }
    public void setTratamientos(List<IncidenciaTratamiento> tratamientos) { this.tratamientos = tratamientos; }

    // helpers para mantener relación bidireccional si los usas
    public void addTratamiento(IncidenciaTratamiento t) {
        tratamientos.add(t);
        t.setIncidenciaEnfermedad(this);
    }

    public void removeTratamiento(IncidenciaTratamiento t) {
        tratamientos.remove(t);
        t.setIncidenciaEnfermedad(null);
    }
}
