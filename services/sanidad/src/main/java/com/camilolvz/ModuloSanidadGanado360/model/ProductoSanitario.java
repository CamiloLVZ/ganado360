package com.camilolvz.ModuloSanidadGanado360.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
public class ProductoSanitario {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(unique = true, nullable = false)
    private String nombre;

    @Column(nullable = true)
    private String tipo;

    /**
     * Guardamos múltiples especies como una colección de strings.
     * Se usará una tabla separada manejada por JPA.
     */
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "producto_sanitario_especies",
            joinColumns = @JoinColumn(name = "producto_sanitario_id")
    )
    @Column(name = "especie")
    private List<String> especies = new ArrayList<>();

    public ProductoSanitario() {}

    public ProductoSanitario(String nombre, String tipo, List<String> especies) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.especies = especies != null ? especies : new ArrayList<>();
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public List<String> getEspecies() { return especies; }
    public void setEspecies(List<String> especies) {
        this.especies = especies != null ? especies : new ArrayList<>();
    }
}
