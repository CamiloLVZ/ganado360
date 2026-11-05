package com.ganado.sanidad.repository;

import com.ganado.sanidad.model.IncidenciaVacuna;
import com.ganado.sanidad.model.EstadoIncidencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface IncidenciaVacunaRepository extends JpaRepository<IncidenciaVacuna, UUID> {

    List<IncidenciaVacuna> findByIdAnimal(UUID idAnimal);

    List<IncidenciaVacuna> findByEstado(EstadoIncidencia estado);

    List<IncidenciaVacuna> findByProductoUsadoId(UUID productoId);
}
