package com.ganado.gateway.dto.user;

import lombok.Data;

@Data
public class UserRequestDTO {
    private String nombre;
    private String email;
    private String password; // opcional en update
}
