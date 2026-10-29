package com.camilolvz.ModuloSanidadGanado360.model;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
public class TratamientoSanitario {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(unique = true, nullable = false)
    private String nombre;

    @Column(columnDefinition = "TEXT", nullable = true)
    private String descripcion;

    // nuevos campos
    private String medicamento;
    private String dosis;

    // duracion total: cantidad + unidad
    private Integer duracionTotalCantidad;

    @Enumerated(EnumType.STRING)
    private TiempoUnidad duracionTotalUnidad;

    // tiempo entre incidencias: cantidad + unidad
    private Integer intervaloCantidad;

    @Enumerated(EnumType.STRING)
    private TiempoUnidad intervaloUnidad;

    public TratamientoSanitario() {}

    public TratamientoSanitario(String nombre, String descripcion, String medicamento, String dosis,
                                Integer duracionTotalCantidad, TiempoUnidad duracionTotalUnidad,
                                Integer intervaloCantidad, TiempoUnidad intervaloUnidad) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.medicamento = medicamento;
        this.dosis = dosis;
        this.duracionTotalCantidad = duracionTotalCantidad;
        this.duracionTotalUnidad = duracionTotalUnidad;
        this.intervaloCantidad = intervaloCantidad;
        this.intervaloUnidad = intervaloUnidad;
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getMedicamento() { return medicamento; }
    public void setMedicamento(String medicamento) { this.medicamento = medicamento; }

    public String getDosis() { return dosis; }
    public void setDosis(String dosis) { this.dosis = dosis; }

    public Integer getDuracionTotalCantidad() { return duracionTotalCantidad; }
    public void setDuracionTotalCantidad(Integer duracionTotalCantidad) { this.duracionTotalCantidad = duracionTotalCantidad; }

    public TiempoUnidad getDuracionTotalUnidad() { return duracionTotalUnidad; }
    public void setDuracionTotalUnidad(TiempoUnidad duracionTotalUnidad) { this.duracionTotalUnidad = duracionTotalUnidad; }

    public Integer getIntervaloCantidad() { return intervaloCantidad; }
    public void setIntervaloCantidad(Integer intervaloCantidad) { this.intervaloCantidad = intervaloCantidad; }

    public TiempoUnidad getIntervaloUnidad() { return intervaloUnidad; }
    public void setIntervaloUnidad(TiempoUnidad intervaloUnidad) { this.intervaloUnidad = intervaloUnidad; }
}
