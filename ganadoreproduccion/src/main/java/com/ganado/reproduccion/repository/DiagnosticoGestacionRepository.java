package com.ganado.reproduccion.repository;

import com.ganado.reproduccion.model.DiagnosticoGestacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface DiagnosticoGestacionRepository extends JpaRepository<DiagnosticoGestacion, UUID> {
    List<DiagnosticoGestacion> findByIdMonta(UUID idMonta);
}
