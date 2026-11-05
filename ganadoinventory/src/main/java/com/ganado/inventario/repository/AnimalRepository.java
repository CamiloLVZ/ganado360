package com.ganado.inventario.repository;

import com.ganado.inventario.model.Animal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface AnimalRepository extends JpaRepository<Animal, UUID> {

    List<Animal> findByEspecieIgnoreCase(String especie);

    List<Animal> findByFincaId(UUID fincaId);

    List<Animal> findByFincaIdAndEstado(UUID fincaId, String estado);
}
