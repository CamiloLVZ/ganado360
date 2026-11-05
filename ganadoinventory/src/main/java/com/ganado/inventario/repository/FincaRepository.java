package com.ganado.inventario.repository;

import com.ganado.inventario.model.Finca;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FincaRepository extends JpaRepository<Finca, UUID> {
}
