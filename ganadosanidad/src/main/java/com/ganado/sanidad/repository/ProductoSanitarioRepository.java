package com.ganado.sanidad.repository;

import com.ganado.sanidad.model.ProductoSanitario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface ProductoSanitarioRepository extends JpaRepository<ProductoSanitario, UUID> {
    ProductoSanitario findByNombreIgnoreCase(String nombre);

    /**
     * Busca productos que tengan en su lista de especies el valor proporcionado (case-insensitive).
     */
    @Query("select p from ProductoSanitario p join p.especies e where lower(e) = lower(:especie)")
    List<ProductoSanitario> findByEspecieIgnoreCase(@Param("especie") String especie);
}
