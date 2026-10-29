package com.ganado.usuarios.mapper;


import com.ganado.usuarios.dto.UserRequestDTO;
import com.ganado.usuarios.dto.UserResponseDTO;
import com.ganado.usuarios.model.Usuario;

public class UsuarioMapper {

    public static UserResponseDTO toDTO(Usuario user) {
        return UserResponseDTO.builder()
                .id(user.getId())
                .nombre(user.getNombre())
                .email(user.getEmail())
                .fechaCreacion(user.getFechaCreacion())
                .build();
    }

    public static void updateEntity(Usuario user, UserRequestDTO dto) {
        user.setNombre(dto.getNombre());
        user.setEmail(dto.getEmail());
        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            user.setPassword(dto.getPassword()); // se encodea luego en service
        }
    }
}
