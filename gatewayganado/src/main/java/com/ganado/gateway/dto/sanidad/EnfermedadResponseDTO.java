package com.ganado.gateway.dto.sanidad;


import java.util.UUID;

public class EnfermedadResponseDTO {

    private UUID id;
    private String nombre;
    private String descripcion;

    public EnfermedadResponseDTO() {}

    public EnfermedadResponseDTO(UUID id, String nombre, String descripcion) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
}
