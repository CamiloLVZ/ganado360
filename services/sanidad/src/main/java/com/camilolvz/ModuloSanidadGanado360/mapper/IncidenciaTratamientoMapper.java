package com.camilolvz.ModuloSanidadGanado360.mapper;

import com.camilolvz.ModuloSanidadGanado360.dto.IncidenciaTratamientoRequestDTO;
import com.camilolvz.ModuloSanidadGanado360.dto.IncidenciaTratamientoResponseDTO;
import com.camilolvz.ModuloSanidadGanado360.model.EstadoIncidencia;
import com.camilolvz.ModuloSanidadGanado360.model.IncidenciaTratamiento;
import com.camilolvz.ModuloSanidadGanado360.model.TratamientoSanitario;
import com.camilolvz.ModuloSanidadGanado360.repository.TratamientoSanitarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class IncidenciaTratamientoMapper {

    private final TratamientoSanitarioRepository tratamientoRepository;

    public IncidenciaTratamientoMapper(TratamientoSanitarioRepository tratamientoRepository) {
        this.tratamientoRepository = tratamientoRepository;
    }

    /**
     * Entidad -> ResponseDTO
     */
    public IncidenciaTratamientoResponseDTO toDTO(IncidenciaTratamiento e) {
        if (e == null) return null;

        IncidenciaTratamientoResponseDTO dto = new IncidenciaTratamientoResponseDTO();
        dto.setId(e.getId());
        dto.setIdAnimal(e.getIdAnimal());

        if (e.getTratamiento() != null) {
            dto.setTratamientoId(e.getTratamiento().getId());
            // Asume getNombre(); si tu entidad usa otro getter, cámbialo aquí.
            try {
                dto.setTratamientoNombre(e.getTratamiento().getNombre());
            } catch (Exception ex) {
                dto.setTratamientoNombre(null);
            }
        } else {
            dto.setTratamientoId(null);
            dto.setTratamientoNombre(null);
        }

        dto.setResponsable(e.getResponsable());
        dto.setFechaTratamiento(e.getFechaTratamiento());
        dto.setEstado(e.getEstado());
        return dto;
    }

    /**
     * RequestDTO -> Entidad (para creación).
     * Lanza ResponseStatusException en los mismos casos que tenías antes.
     */
    public IncidenciaTratamiento toEntity(IncidenciaTratamientoRequestDTO req) {
        IncidenciaTratamiento i = new IncidenciaTratamiento();

        // Tratamiento (es obligatorio en tu DTO)
        if (req.getTratamientoId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "tratamientoId es obligatorio");
        }
        TratamientoSanitario tratamiento = tratamientoRepository.findById(req.getTratamientoId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tratamiento no encontrado con id: " + req.getTratamientoId()));
        i.setTratamiento(tratamiento);

        i.setIdAnimal(req.getIdAnimal());
        i.setResponsable(req.getResponsable());
        i.setFechaTratamiento(req.getFechaTratamiento());

        if (req.getEstado() != null && !req.getEstado().isBlank()) {
            try {
                i.setEstado(EstadoIncidencia.valueOf(req.getEstado().toUpperCase()));
            } catch (IllegalArgumentException ex) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Estado inválido. Valores permitidos: PENDIENTE, REALIZADO, ANULADO");
            }
        } else {
            i.setEstado(EstadoIncidencia.PENDIENTE);
        }

        return i;
    }

    /**
     * Actualización parcial: aplica el RequestDTO sobre una entidad existente.
     * Reproduce la lógica que había en el service para la actualización parcial.
     */
    public void updateEntityFromRequest(
            IncidenciaTratamientoRequestDTO req,
            IncidenciaTratamiento entity,
            TratamientoSanitarioRepository tratamientoRepository
    ) {
        if (req.getTratamientoId() != null) {
            TratamientoSanitario t = tratamientoRepository.findById(req.getTratamientoId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                            "Tratamiento no encontrado con id: " + req.getTratamientoId()));
            entity.setTratamiento(t);
        }

        if (req.getIdAnimal() != null) entity.setIdAnimal(req.getIdAnimal());
        if (req.getResponsable() != null && !req.getResponsable().isBlank()) entity.setResponsable(req.getResponsable());
        if (req.getFechaTratamiento() != null) entity.setFechaTratamiento(req.getFechaTratamiento());

        if (req.getEstado() != null && !req.getEstado().isBlank()) {
            try {
                entity.setEstado(EstadoIncidencia.valueOf(req.getEstado().toUpperCase()));
            } catch (IllegalArgumentException ex) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "Estado inválido. Valores permitidos: PENDIENTE, REALIZADO, ANULADO");
            }
        }
    }

    /**
     * Helpers para listas
     */
    public List<IncidenciaTratamientoResponseDTO> toDTOList(List<IncidenciaTratamiento> list) {
        return list.stream().map(this::toDTO).collect(Collectors.toList());
    }
}
