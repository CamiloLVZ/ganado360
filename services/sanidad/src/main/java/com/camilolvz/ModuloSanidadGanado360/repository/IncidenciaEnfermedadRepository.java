package com.camilolvz.ModuloSanidadGanado360.repository;

import com.camilolvz.ModuloSanidadGanado360.model.IncidenciaEnfermedad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface IncidenciaEnfermedadRepository extends JpaRepository<IncidenciaEnfermedad, UUID> {

    List<IncidenciaEnfermedad> findByIdAnimal(UUID idAnimal);

    List<IncidenciaEnfermedad> findByEnfermedadId(UUID enfermedadId);
}
