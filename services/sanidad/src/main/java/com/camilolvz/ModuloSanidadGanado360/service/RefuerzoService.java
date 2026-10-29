package com.camilolvz.ModuloSanidadGanado360.service;

import com.camilolvz.ModuloSanidadGanado360.dto.RefuerzoRequestDTO;
import com.camilolvz.ModuloSanidadGanado360.dto.RefuerzoResponseDTO;
import com.camilolvz.ModuloSanidadGanado360.mapper.RefuerzoMapper;
import com.camilolvz.ModuloSanidadGanado360.model.*;
import com.camilolvz.ModuloSanidadGanado360.repository.IncidenciaVacunaRepository;
import com.camilolvz.ModuloSanidadGanado360.repository.RefuerzoRepository;
import com.camilolvz.ModuloSanidadGanado360.repository.ProductoSanitarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class RefuerzoService {

    private final RefuerzoRepository repository;
    private final ProductoSanitarioRepository productoRepository;
    private final IncidenciaVacunaRepository incidenciaVacunaRepository;
    private final RefuerzoMapper mapper;

    public RefuerzoService(RefuerzoRepository repository,
                           ProductoSanitarioRepository productoRepository,
                           IncidenciaVacunaRepository incidenciaVacunaRepository,
                           RefuerzoMapper mapper) {
        this.repository = repository;
        this.productoRepository = productoRepository;
        this.incidenciaVacunaRepository = incidenciaVacunaRepository;
        this.mapper = mapper;
    }

    // CRUD básico
    public List<RefuerzoResponseDTO> listarTodos() {
        return repository.findAll().stream().map(mapper::toDto).collect(Collectors.toList());
    }

    public RefuerzoResponseDTO crear(RefuerzoRequestDTO req) {
        var producto = productoRepository.findById(req.getProductoId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Producto (vacuna) no encontrado"));

        Refuerzo r = mapper.toEntity(req, producto);
        Refuerzo saved = repository.save(r);
        return mapper.toDto(saved);
    }

    public Optional<RefuerzoResponseDTO> obtener(UUID id) {
        return repository.findById(id).map(mapper::toDto);
    }

    public Optional<RefuerzoResponseDTO> editar(UUID id, RefuerzoRequestDTO req) {
        return repository.findById(id).map(existing -> {
            ProductoSanitario producto = null;
            if (req.getProductoId() != null) {
                producto = productoRepository.findById(req.getProductoId())
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Producto no encontrado"));
            }
            mapper.updateEntityFromDto(existing, req, producto);
            Refuerzo saved = repository.save(existing);
            return mapper.toDto(saved);
        });
    }

    public boolean eliminar(UUID id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<RefuerzoResponseDTO> buscarPorProducto(UUID productoId) {
        return repository.findByProductoId(productoId).stream().map(mapper::toDto).collect(Collectors.toList());
    }

    /**
     * Genera incidencias vacunales adicionales según los refuerzos definidos para el PRODUCTO (vacuna)
     * de la incidencia base. Devuelve lista de incidencias creadas.
     *
     * NO usa IncidenciaVacunaService para evitar ciclos; guarda con IncidenciaVacunaRepository.
     */
    public List<IncidenciaVacuna> generarIncidenciasPorRefuerzos(IncidenciaVacuna base) {
        if (base.getProductoUsado() == null) return Collections.emptyList();

        List<Refuerzo> refuerzos = repository.findByProductoId(base.getProductoUsado().getId());
        if (refuerzos == null || refuerzos.isEmpty()) return Collections.emptyList();

        LocalDate fechaBase = toLocalDate(base.getFechaVacunacion());
        List<IncidenciaVacuna> creadas = new ArrayList<>();

        for (Refuerzo r : refuerzos) {
            LocalDate nuevaFecha = fechaBase.plus(r.getCantidad(), r.getUnidad().toChronoUnit());
            IncidenciaVacuna nueva = new IncidenciaVacuna();

            // copiar campos relevantes de la base
            nueva.setProductoUsado(base.getProductoUsado());
            nueva.setIdAnimal(base.getIdAnimal());
            nueva.setResponsable(base.getResponsable());
            nueva.setFechaVacunacion(fromLocalDate(nuevaFecha));
            nueva.setEstado(EstadoIncidencia.PENDIENTE);

            IncidenciaVacuna saved = incidenciaVacunaRepository.save(nueva);
            creadas.add(saved);
        }

        return creadas;
    }

    private LocalDate toLocalDate(Date d) {
        return d.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    private Date fromLocalDate(LocalDate ld) {
        return Date.from(ld.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }
}
