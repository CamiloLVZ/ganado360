package com.ganado.usuarios.dto;

import lombok.Data;

@Data
public class UserRequestDTO {
    private String nombre;
    private String email;
    private String password; // opcional en update
}