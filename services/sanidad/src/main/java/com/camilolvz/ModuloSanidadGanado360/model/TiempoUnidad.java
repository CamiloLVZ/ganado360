package com.camilolvz.ModuloSanidadGanado360.model;

import java.time.temporal.ChronoUnit;

public enum TiempoUnidad {
    DIAS,
    SEMANAS,
    MESES,
    ANOS; // años

    public ChronoUnit toChronoUnit() {
        return switch (this) {
            case DIAS -> ChronoUnit.DAYS;
            case SEMANAS -> ChronoUnit.WEEKS;
            case MESES -> ChronoUnit.MONTHS;
            case ANOS -> ChronoUnit.YEARS;
        };
    }
}
