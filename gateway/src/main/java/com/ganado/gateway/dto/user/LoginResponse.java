package com.ganado.gateway.dto.user;

import com.ganado.gateway.model.Usuario;

public record LoginResponse(
        String token,
        Usuario user
) {}