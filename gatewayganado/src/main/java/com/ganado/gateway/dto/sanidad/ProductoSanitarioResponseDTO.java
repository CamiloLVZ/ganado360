package com.ganado.gateway.dto.sanidad;

import java.util.List;
import java.util.UUID;

public class ProductoSanitarioResponseDTO {

    private UUID id;
    private String nombre;
    private String tipo;
    private List<String> especies;

    public ProductoSanitarioResponseDTO() {}

    public ProductoSanitarioResponseDTO(UUID id, String nombre, String tipo, List<String> especies) {
        this.id = id;
        this.nombre = nombre;
        this.tipo = tipo;
        this.especies = especies;
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public List<String> getEspecies() { return especies; }
    public void setEspecies(List<String> especies) { this.especies = especies; }
}
