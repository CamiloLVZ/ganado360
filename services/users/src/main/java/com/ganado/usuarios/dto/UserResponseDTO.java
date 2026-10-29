package com.ganado.usuarios.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class UserResponseDTO {
    private UUID id;
    private String nombre;
    private String email;
    private LocalDateTime fechaCreacion;
}
