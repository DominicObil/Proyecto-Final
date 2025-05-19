package org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * DTO para crear la disponibilidad de mesas en un restaurante.
 *
 * Utilizado por los administradores para definir la capacidad de un restaurante
 * en un rango horario específico para una fecha concreta.
 */
@Data
@Schema(name = "DisponibilidadMesaCreateDTO", description = "DTO para definir la disponibilidad de mesas en un restaurante.")
public class DisponibilidadMesaCreateDTO {

    @NotNull(message = "{msg.disponibilidad.restauranteId.notNull}")
    @Schema(description = "ID del restaurante al que pertenece la disponibilidad.", example = "1")
    private Long restauranteId;

    @NotNull(message = "{msg.disponibilidad.fecha.notNull}")
    @FutureOrPresent(message = "{msg.disponibilidad.fecha.futureOrPresent}")
    @Schema(description = "Fecha de disponibilidad.", example = "2025-06-10")
    private LocalDate fecha;

    @NotNull(message = "{msg.disponibilidad.horaInicio.notNull}")
    @Schema(description = "Hora de inicio del rango de disponibilidad.", example = "13:00:00")
    private LocalTime horaInicio;

    @NotNull(message = "{msg.disponibilidad.horaFin.notNull}")
    @Schema(description = "Hora de fin del rango de disponibilidad.", example = "15:00:00")
    private LocalTime horaFin;

    @NotNull(message = "{msg.disponibilidad.mesas.notNull}")
    @Positive(message = "{msg.disponibilidad.mesas.positive}")
    @Schema(description = "Número de mesas disponibles en ese rango horario.", example = "10")
    private Integer mesasDisponibles;
}
