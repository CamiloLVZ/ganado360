package com.ganado.inventario.repository;

import com.ganado.inventario.model.Animal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface AnimalRepository extends JpaRepository<Animal, UUID> {

    /**
     * Busca animales donde la propiedad 'especie' (lista o string en la entidad) contenga
     * el valor dado, case-insensitive.
     *
     * Si tu entidad tiene 'especie' como String:
     *   -> use: List<Animal> findByEspecieIgnoreCase(String especie);
     *
     * Si 'especie' es una colección u otro modelo, reemplaza la query según corresponda.
     */
    List<Animal> findByEspecieIgnoreCase(String especie);

    // Si prefieres una query explícita:
    // @Query("select a from Animal a where lower(a.especie) = lower(:especie)")
    // List<Animal> findByEspecieIgnoreCase(@Param("especie") String especie);
}
