package com.ganado.reproduccion.repository;

import com.ganado.reproduccion.model.Nacimiento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface NacimientoRepository extends JpaRepository<Nacimiento, UUID> {
    List<Nacimiento> findByIdMadre(UUID idMadre);

}
