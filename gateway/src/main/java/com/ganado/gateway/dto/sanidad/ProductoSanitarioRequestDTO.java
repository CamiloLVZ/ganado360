package com.ganado.gateway.dto.sanidad;


import jakarta.validation.constraints.NotBlank;
import java.util.List;

public class ProductoSanitarioRequestDTO {

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    private String tipo;

    /**
     * Ahora aceptamos una lista de especies.
     */
    private List<String> especies;

    public ProductoSanitarioRequestDTO() {}

    public ProductoSanitarioRequestDTO(String nombre, String tipo, List<String> especies) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.especies = especies;
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public List<String> getEspecies() { return especies; }
    public void setEspecies(List<String> especies) { this.especies = especies; }
}
