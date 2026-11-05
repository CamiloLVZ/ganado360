package com.ganado.inventario.repository;

import com.ganado.inventario.model.AnimalHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface AnimalHistoryRepository extends JpaRepository<AnimalHistory, UUID> {

    List<AnimalHistory> findAllByAnimal_IdOrderByFechaCreacionDesc(UUID animalId);
}
