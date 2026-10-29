package com.camilolvz.ModuloSanidadGanado360.service;

import com.camilolvz.ModuloSanidadGanado360.dto.IncidenciaTratamientoRequestDTO;
import com.camilolvz.ModuloSanidadGanado360.dto.IncidenciaTratamientoResponseDTO;
import com.camilolvz.ModuloSanidadGanado360.mapper.IncidenciaTratamientoMapper;
import com.camilolvz.ModuloSanidadGanado360.model.*;
import com.camilolvz.ModuloSanidadGanado360.repository.IncidenciaEnfermedadRepository;
import com.camilolvz.ModuloSanidadGanado360.repository.IncidenciaTratamientoRepository;
import com.camilolvz.ModuloSanidadGanado360.repository.TratamientoSanitarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class IncidenciaTratamientoService {

    private final IncidenciaTratamientoRepository repository;
    private final TratamientoSanitarioRepository tratamientoRepository;
    private final IncidenciaTratamientoMapper mapper;
    private final IncidenciaEnfermedadRepository incidenciaEnfermedadRepository; // NUEVO
    private final IncidenciaEnfermedadService incidenciaEnfermedadService;

    public IncidenciaTratamientoService(
            IncidenciaTratamientoRepository repository,
            TratamientoSanitarioRepository tratamientoRepository,
            IncidenciaTratamientoMapper mapper,
            IncidenciaEnfermedadRepository incidenciaEnfermedadRepository, // inyectar repo
            IncidenciaEnfermedadService incidenciaEnfermedadService
    ) {
        this.repository = repository;
        this.tratamientoRepository = tratamientoRepository;
        this.mapper = mapper;
        this.incidenciaEnfermedadRepository = incidenciaEnfermedadRepository;
        this.incidenciaEnfermedadService = incidenciaEnfermedadService;
    }


    /**
     * Crea la incidencia base y genera (si aplica) las incidencias programadas
     * usando duracionTotal + intervalo del Tratamiento.
     */
    @Transactional
    public IncidenciaTratamientoResponseDTO crear(IncidenciaTratamientoRequestDTO req) {
        if (req.getFechaTratamiento() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "fechaTratamiento es obligatoria");
        }
        if (req.getTratamientoId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "tratamientoId es obligatorio");
        }

        TratamientoSanitario tratamiento = tratamientoRepository.findById(req.getTratamientoId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "Tratamiento no encontrado con id: " + req.getTratamientoId()));

        if (hayIncidenciasActivas(req.getIdAnimal(), tratamiento.getId())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Ya existen incidencias activas para este animal y tratamiento");
        }

        IncidenciaTratamiento base = mapper.toEntity(req);
        base.setTratamiento(tratamiento);

        // --- NUEVO: vincular IncidenciaEnfermedad si viene en el DTO ---
        if (req.getIncidenciaEnfermedadId() != null) {
            IncidenciaEnfermedad incidenciaEnf = incidenciaEnfermedadRepository.findById(req.getIncidenciaEnfermedadId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                            "IncidenciaEnfermedad no encontrada con id: " + req.getIncidenciaEnfermedadId()));

            // Validación de seguridad: misma mascota/animal
            if (incidenciaEnf.getIdAnimal() != null && !incidenciaEnf.getIdAnimal().equals(req.getIdAnimal())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "La incidencia de enfermedad no pertenece al mismo animal indicado en la incidencia de tratamiento");
            }

            base.setIncidenciaEnfermedad(incidenciaEnf);
        }
        // --- FIN NUEVO ---

        IncidenciaTratamiento savedBase = repository.save(base);

        try {
            generarIncidenciasProgramadas(savedBase, tratamiento);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error generando incidencias programadas: " + ex.getMessage());
        }

        if (req.getIncidenciaEnfermedadId() != null) {
            incidenciaEnfermedadService.recalcularEstado(req.getIncidenciaEnfermedadId());
        }
        return mapper.toDTO(savedBase);
    }



    // Listar todos
    public List<IncidenciaTratamientoResponseDTO> listarTodos() {
        return mapper.toDTOList(repository.findAll());
    }

    public IncidenciaTratamientoResponseDTO obtener(UUID id) {
        return repository.findById(id).map(mapper::toDTO)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Incidencia no encontrada"));
    }

    // Obtener por animal
    public List<IncidenciaTratamientoResponseDTO> obtenerPorAnimal(UUID idAnimal) {
        return repository.findByIdAnimal(idAnimal).stream().map(mapper::toDTO).collect(Collectors.toList());
    }

    // Obtener por tratamiento
    public List<IncidenciaTratamientoResponseDTO> obtenerPorTratamiento(UUID tratamientoId) {
        return repository.findByTratamiento_Id(tratamientoId).stream().map(mapper::toDTO).collect(Collectors.toList());
    }

    // Obtener por estado
    public List<IncidenciaTratamientoResponseDTO> obtenerPorEstado(EstadoIncidencia estado) {
        return repository.findByEstado(estado).stream().map(mapper::toDTO).collect(Collectors.toList());
    }

    // Actualizar (parcial)
    public IncidenciaTratamientoResponseDTO actualizar(UUID id, IncidenciaTratamientoRequestDTO req) {

        IncidenciaTratamiento existente = repository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Incidencia no encontrada"));

        // Delegar actualización al mapper
        mapper.updateEntityFromRequest(req, existente, tratamientoRepository);

        IncidenciaTratamiento guardada = repository.save(existente);

        if (req.getIncidenciaEnfermedadId() != null) {
            incidenciaEnfermedadService.recalcularEstado(req.getIncidenciaEnfermedadId());
        }

        return mapper.toDTO(guardada);
    }

    // "Eliminar" lógico: marcar ANULADO
    public IncidenciaTratamientoResponseDTO anular(UUID id) {

        IncidenciaTratamiento existente = repository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Incidencia no encontrada"));

        existente.setEstado(EstadoIncidencia.ANULADO);

        IncidenciaTratamiento guardada = repository.save(existente);

        if (existente.getIncidenciaEnfermedad().getId() != null) {
            incidenciaEnfermedadService.recalcularEstado(existente.getIncidenciaEnfermedad().getId());
        }
        return mapper.toDTO(guardada);
    }


    /**
     * Comprueba si hay incidencias activas para un animal y tratamiento.
     * Consideramos 'activo' a todo estado distinto de ANULADO.
     */
    public boolean hayIncidenciasActivas(UUID idAnimal, UUID tratamientoId) {
        return repository.existsByIdAnimalAndTratamiento_IdAndEstadoNot(idAnimal, tratamientoId, EstadoIncidencia.ANULADO);
    }

    /**
     * Lógica para generar incidencias programadas:
     * - Usa duracionTotalCantidad/unidad y intervaloCantidad/unidad del TratamientoSanitario
     * - Crea incidencias en fechas: start + intervalo, start + 2*intervalo, ... hasta <= start + duracionTotal
     * - Evita duplicados verificando si ya existe incidencia para misma fecha (mismo animal+tratamiento).
     */
    private void generarIncidenciasProgramadas(IncidenciaTratamiento base, TratamientoSanitario t) {
        if (t == null) return;

        Integer durTotalCantidad = t.getDuracionTotalCantidad();
        TiempoUnidad durTotalUnidad = t.getDuracionTotalUnidad();

        Integer intervaloCantidad = t.getIntervaloCantidad();
        TiempoUnidad intervaloUnidad = t.getIntervaloUnidad();

        if (durTotalCantidad == null || durTotalCantidad <= 0 || durTotalUnidad == null) return;
        if (intervaloCantidad == null || intervaloCantidad <= 0 || intervaloUnidad == null) return;

        LocalDate fechaBase = toLocalDate(base.getFechaTratamiento());
        LocalDate fechaFin = fechaBase.plus(durTotalCantidad, durTotalUnidad.toChronoUnit());

        LocalDate siguiente = fechaBase.plus(intervaloCantidad, intervaloUnidad.toChronoUnit());

        List<IncidenciaTratamiento> nuevas = new ArrayList<>();
        while (!siguiente.isAfter(fechaFin)) {
            Date fechaSiguienteDate = fromLocalDate(siguiente);

            boolean existe = repository.existsByIdAnimalAndTratamiento_IdAndFechaTratamiento(
                    base.getIdAnimal(), t.getId(), fechaSiguienteDate
            );

            if (!existe) {
                IncidenciaTratamiento nueva = new IncidenciaTratamiento();
                nueva.setTratamiento(t);
                nueva.setIdAnimal(base.getIdAnimal());
                nueva.setResponsable(base.getResponsable());
                nueva.setFechaTratamiento(fechaSiguienteDate);
                nueva.setEstado(EstadoIncidencia.PENDIENTE);
                // Si la incidencia base está vinculada a una IncidenciaEnfermedad, puedes propagarla:
                nueva.setIncidenciaEnfermedad(base.getIncidenciaEnfermedad());

                nuevas.add(nueva);
            }

            siguiente = siguiente.plus(intervaloCantidad, intervaloUnidad.toChronoUnit());
        }

        if (!nuevas.isEmpty()) {
            repository.saveAll(nuevas);
        }
    }

    private LocalDate toLocalDate(Date d) {
        return d.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    private Date fromLocalDate(LocalDate ld) {
        return Date.from(ld.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }
}
