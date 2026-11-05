package com.ganado.sanidad.repository;

import com.ganado.sanidad.model.TratamientoSanitario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TratamientoSanitarioRepository extends JpaRepository<TratamientoSanitario, UUID> {
    TratamientoSanitario findByNombreIgnoreCase(String nombre);
    List<TratamientoSanitario> findByDescripcionContainingIgnoreCase(String fragment);
}
