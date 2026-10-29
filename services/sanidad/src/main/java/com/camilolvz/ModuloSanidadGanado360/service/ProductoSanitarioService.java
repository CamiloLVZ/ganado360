package com.camilolvz.ModuloSanidadGanado360.service;

import com.camilolvz.ModuloSanidadGanado360.dto.ProductoSanitarioRequestDTO;
import com.camilolvz.ModuloSanidadGanado360.dto.ProductoSanitarioResponseDTO;
import com.camilolvz.ModuloSanidadGanado360.mapper.ProductoSanitarioMapper;
import com.camilolvz.ModuloSanidadGanado360.model.ProductoSanitario;
import com.camilolvz.ModuloSanidadGanado360.repository.ProductoSanitarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProductoSanitarioService {

    private final ProductoSanitarioRepository repository;
    private final ProductoSanitarioMapper mapper;

    public ProductoSanitarioService(ProductoSanitarioRepository repository,
                                    ProductoSanitarioMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    // Listar todos (Response DTOs)
    public List<ProductoSanitarioResponseDTO> listarTodos() {
        return repository.findAll().stream()
                .map(mapper::toResponseDto)
                .collect(Collectors.toList());
    }

    // Crear a partir de RequestDTO
    public ProductoSanitarioResponseDTO crear(ProductoSanitarioRequestDTO dto) {
        ProductoSanitario entity = mapper.toEntity(dto);
        ProductoSanitario saved = repository.save(entity);
        return mapper.toResponseDto(saved);
    }

    // Obtener por id (ResponseDTO)
    public Optional<ProductoSanitarioResponseDTO> obtenerPorId(UUID id) {
        return repository.findById(id).map(mapper::toResponseDto);
    }

    // Mantener acceso directo a la entidad para servicios que requieran la entidad
    public Optional<ProductoSanitario> obtenerEntityPorId(UUID id){
        return repository.findById(id);
    }

    // Editar (si existe) usando RequestDTO
    public Optional<ProductoSanitarioResponseDTO> editar(UUID id, ProductoSanitarioRequestDTO dto) {
        return repository.findById(id).map(existing -> {
            mapper.updateEntityFromRequest(existing, dto);
            ProductoSanitario updated = repository.save(existing);
            return mapper.toResponseDto(updated);
        });
    }

    // Eliminar
    public boolean eliminar(UUID id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    // Buscar por nombre (entrega ResponseDTO o null)
    public ProductoSanitarioResponseDTO buscarPorNombre(String nombre) {
        ProductoSanitario p = repository.findByNombreIgnoreCase(nombre);
        return p != null ? mapper.toResponseDto(p) : null;
    }

    // Obtener o crear por nombre (mantiene tipo/especie si se crea a partir del DTO opcional)
    public ProductoSanitarioResponseDTO obtenerOcrear(String nombre, ProductoSanitarioRequestDTO fallbackDto) {
        ProductoSanitario existing = repository.findByNombreIgnoreCase(nombre);
        if (existing != null) return mapper.toResponseDto(existing);

        ProductoSanitario toSave = new ProductoSanitario();
        toSave.setNombre(nombre);
        if (fallbackDto != null) {
            toSave.setTipo(fallbackDto.getTipo());
            toSave.setEspecies(fallbackDto.getEspecies()); // <- lista ahora
        }
        ProductoSanitario saved = repository.save(toSave);
        return mapper.toResponseDto(saved);
    }

    // Buscar por especie (string)
    public List<ProductoSanitarioResponseDTO> buscarPorEspecie(String especie) {
        return repository.findByEspecieIgnoreCase(especie)
                .stream()
                .map(mapper::toResponseDto)
                .collect(Collectors.toList());
    }
}
