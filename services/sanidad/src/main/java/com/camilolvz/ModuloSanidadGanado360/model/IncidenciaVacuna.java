package com.camilolvz.ModuloSanidadGanado360.model;

import jakarta.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
public class IncidenciaVacuna {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    // Relación SQL con ProductoSanitario
    @ManyToOne(optional = false)
    @JoinColumn(name = "producto_id")
    private ProductoSanitario productoUsado;

    // UUID del animal (está en otro microservicio)
    @Column(nullable = false)
    private UUID idAnimal;

    @Column(nullable = false)
    private String responsable;

    @Temporal(TemporalType.DATE) // solo fecha (sin hora)
    @Column(nullable = false)
    private Date fechaVacunacion;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoIncidencia estado = EstadoIncidencia.PENDIENTE;

    public IncidenciaVacuna() {}

    public IncidenciaVacuna(ProductoSanitario productoUsado, UUID idAnimal, String responsable, Date fechaVacunacion, EstadoIncidencia estado) {
        this.productoUsado = productoUsado;
        this.idAnimal = idAnimal;
        this.responsable = responsable;
        this.fechaVacunacion = fechaVacunacion;
        this.estado = estado;
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public ProductoSanitario getProductoUsado() { return productoUsado; }
    public void setProductoUsado(ProductoSanitario productoUsado) { this.productoUsado = productoUsado; }

    public UUID getIdAnimal() { return idAnimal; }
    public void setIdAnimal(UUID idAnimal) { this.idAnimal = idAnimal; }

    public String getResponsable() { return responsable; }
    public void setResponsable(String responsable) { this.responsable = responsable; }

    public Date getFechaVacunacion() { return fechaVacunacion; }
    public void setFechaVacunacion(Date fechaVacunacion) { this.fechaVacunacion = fechaVacunacion; }

    public EstadoIncidencia getEstado() { return estado; }
    public void setEstado(EstadoIncidencia estado) { this.estado = estado; }
}

