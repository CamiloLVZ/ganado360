package com.ganado.gateway.dto.sanidad;


import java.util.UUID;

public class TratamientoResponseDTO {

    private UUID id;
    private String nombre;
    private String descripcion;

    private String medicamento;
    private String dosis;

    private Integer duracionTotalCantidad;
    private TiempoUnidadDTO duracionTotalUnidad;

    private Integer intervaloCantidad;
    private TiempoUnidadDTO intervaloUnidad;

    public TratamientoResponseDTO() {}

    public TratamientoResponseDTO(UUID id, String nombre, String descripcion,
                                  String medicamento, String dosis,
                                  Integer duracionTotalCantidad, TiempoUnidadDTO duracionTotalUnidad,
                                  Integer intervaloCantidad, TiempoUnidadDTO intervaloUnidad) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.medicamento = medicamento;
        this.dosis = dosis;
        this.duracionTotalCantidad = duracionTotalCantidad;
        this.duracionTotalUnidad = duracionTotalUnidad;
        this.intervaloCantidad = intervaloCantidad;
        this.intervaloUnidad = intervaloUnidad;
    }

    // getters y setters
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

    public TiempoUnidadDTO getDuracionTotalUnidad() { return duracionTotalUnidad; }
    public void setDuracionTotalUnidad(TiempoUnidadDTO duracionTotalUnidad) { this.duracionTotalUnidad = duracionTotalUnidad; }

    public Integer getIntervaloCantidad() { return intervaloCantidad; }
    public void setIntervaloCantidad(Integer intervaloCantidad) { this.intervaloCantidad = intervaloCantidad; }

    public TiempoUnidadDTO getIntervaloUnidad() { return intervaloUnidad; }
    public void setIntervaloUnidad(TiempoUnidadDTO intervaloUnidad) { this.intervaloUnidad = intervaloUnidad; }
}
