package com.ganado.sanidad.dto;


/**
 * Enum DTO para unidades de tiempo — independiente del model.
 * Mantener los mismos nombres que el enum en model.TiempoUnidad para facilitar mapeo por name().
 */
public enum TiempoUnidadDTO {
    DIAS,
    SEMANAS,
    MESES,
    ANOS
}

