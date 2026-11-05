package com.ganado.reproduccion.repository;

import com.ganado.reproduccion.model.Gestacion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface GestacionRepository extends JpaRepository<Gestacion, UUID> {

    List<Gestacion> findByIdHembra(UUID idHembra);
    List<Gestacion> findByIdMonta(UUID idMonta);
    Optional<Gestacion> findByIdMontaAndEstado(UUID idMonta, Gestacion.EstadoGestacion estado);
}
