package com.camilolvz.ModuloSanidadGanado360.repository;

import com.camilolvz.ModuloSanidadGanado360.model.Refuerzo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface RefuerzoRepository extends JpaRepository<Refuerzo, UUID> {
    List<Refuerzo> findByProductoId(UUID productoId);
}
