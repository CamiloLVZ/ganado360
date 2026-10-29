package com.ganado.gateway.dto.reproduccion;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GenealogiaResponseDTO {
    private UUID madre;
    private UUID padre;
    private UUID hijo;
}