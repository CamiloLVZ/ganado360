package com.camilolvz.ModuloSanidadGanado360.repository;

import com.camilolvz.ModuloSanidadGanado360.model.IncidenciaTratamiento;
import com.camilolvz.ModuloSanidadGanado360.model.EstadoIncidencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Repository
public interface IncidenciaTratamientoRepository extends JpaRepository<IncidenciaTratamiento, UUID> {

    List<IncidenciaTratamiento> findByIdAnimal(UUID idAnimal);
    List<IncidenciaTratamiento> findByIncidenciaEnfermedad_Id(UUID incidenciaEnfermedadId);

    long countByIncidenciaEnfermedad_IdAndEstado(UUID incidenciaEnfermedadId, EstadoIncidencia estado);
    long countByIncidenciaEnfermedad_Id(UUID incidenciaEnfermedadId);
    List<IncidenciaTratamiento> findByEstado(EstadoIncidencia estado);

    // Buscar por tratamiento usando la propiedad anidada tratamiento.id
    List<IncidenciaTratamiento> findByTratamiento_Id(UUID tratamientoId);

    // Comprobar existencia para mismo animal+tratamiento donde estado != ANULADO (útil para "activo")
    boolean existsByIdAnimalAndTratamiento_IdAndEstadoNot(UUID idAnimal, UUID tratamientoId, EstadoIncidencia estado);

    // Comprobar si ya existe incidencia para mismo animal+tratamiento+fecha (evitar duplicados)
    boolean existsByIdAnimalAndTratamiento_IdAndFechaTratamiento(UUID idAnimal, UUID tratamientoId, Date fechaTratamiento);
}
