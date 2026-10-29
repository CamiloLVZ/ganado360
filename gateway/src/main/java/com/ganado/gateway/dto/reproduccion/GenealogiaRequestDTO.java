package com.ganado.gateway.dto.reproduccion;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GenealogiaRequestDTO {
    @NotNull(message = "madre es obligatorio")
    private UUID madre;

    // padre opcional
    private UUID padre;

    @NotNull(message = "hijo es obligatorio")
    private UUID hijo;
}
