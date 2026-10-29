package com.camilolvz.ModuloSanidadGanado360.model;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
public class Refuerzo {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "producto_id")
    private ProductoSanitario producto; // antes: enfermedad

    @Column(nullable = false)
    private Integer cantidad; // ej. 6

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TiempoUnidad unidad; // DIAS, SEMANAS, MESES, ANOS

    public Refuerzo() {}

    public Refuerzo(ProductoSanitario producto, Integer cantidad, TiempoUnidad unidad) {
        this.producto = producto;
        this.cantidad = cantidad;
        this.unidad = unidad;
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public ProductoSanitario getProducto() { return producto; }
    public void setProducto(ProductoSanitario producto) { this.producto = producto; }

    public Integer getCantidad() { return cantidad; }
    public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }

    public TiempoUnidad getUnidad() { return unidad; }
    public void setUnidad(TiempoUnidad unidad) { this.unidad = unidad; }
}
