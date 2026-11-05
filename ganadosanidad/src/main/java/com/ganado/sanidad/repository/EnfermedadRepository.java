package com.ganado.sanidad.repository;

import com.ganado.sanidad.model.Enfermedad;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface EnfermedadRepository extends JpaRepository<Enfermedad, UUID> {
    Enfermedad findByNombreIgnoreCase(String nombre);
}
