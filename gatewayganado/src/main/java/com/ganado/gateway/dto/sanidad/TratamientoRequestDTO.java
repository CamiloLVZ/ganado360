package com.ganado.gateway.dto.sanidad;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class TratamientoRequestDTO {

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    private String descripcion;

    // nuevos
    private String medicamento;
    private String dosis;

    @Min(value = 1, message = "duracionTotalCantidad debe ser >= 1")
    private Integer duracionTotalCantidad;

    /**
     * Unidad como String (DIAS|SEMANAS|MESES|ANOS).
     * Mapeo y validación se hacen en el Mapper/Service.
     */
    private String duracionTotalUnidad;

    @Min(value = 1, message = "intervaloCantidad debe ser >= 1")
    private Integer intervaloCantidad;

    private String intervaloUnidad; // DIAS|SEMANAS|MESES|ANOS

    public TratamientoRequestDTO() {}

    // getters y setters
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

    public String getDuracionTotalUnidad() { return duracionTotalUnidad; }
    public void setDuracionTotalUnidad(String duracionTotalUnidad) { this.duracionTotalUnidad = duracionTotalUnidad; }

    public Integer getIntervaloCantidad() { return intervaloCantidad; }
    public void setIntervaloCantidad(Integer intervaloCantidad) { this.intervaloCantidad = intervaloCantidad; }

    public String getIntervaloUnidad() { return intervaloUnidad; }
    public void setIntervaloUnidad(String intervaloUnidad) { this.intervaloUnidad = intervaloUnidad; }
}
