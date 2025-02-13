package org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * DTO para la creación de reservas en restaurantes.
 *
 * <p>Incluye validaciones JSR-303 como {@code @NotNull}, {@code @Size}, {@code @Min}, {@code @Positive}.
 * Estas restricciones se reflejan automáticamente en la documentación generada con {@code springdoc-openapi}.
 * </p>
 *
 * @author [Tu Nombre]
 */
@Data
@Schema(name = "ReservaCreateDTO", description = "DTO para crear una reserva en un restaurante.")
public class ReservaCreateDTO {

    /**
     * Fecha de la reserva.
     * <p>
     * - No puede ser nula. <br>
     * - Debe ser una fecha en el presente o en el futuro. <br>
     * - Formato esperado: YYYY-MM-DD. <br>
     * - Ejemplo: "2025-03-01".
     * </p>
     */
    @NotNull(message = "{msg.reserva.fecha.notNull}")
    @FutureOrPresent(message = "{msg.reserva.fecha.futureOrPresent}")
    @Schema(description = "Fecha de la reserva. Debe ser hoy o en el futuro.", example = "2025-03-01")
    private LocalDate fechaReserva;

    /**
     * Hora de la reserva.
     * <p>
     * - No puede ser nula. <br>
     * - Formato esperado: HH:mm:ss. <br>
     * - Ejemplo: "19:30:00".
     * </p>
     */
    @NotNull(message = "{msg.reserva.hora.notNull}")
    @Schema(description = "Hora de la reserva.", example = "19:30:00")
    private LocalTime horaReserva;

    /**
     * Número de personas en la reserva.
     * <p>
     * - No puede ser nulo. <br>
     * - Debe ser un número positivo mayor o igual a 1. <br>
     * - Ejemplo: "4".
     * </p>
     */
    @NotNull(message = "{msg.reserva.personas.notNull}")
    @Positive(message = "{msg.reserva.personas.positive}")
    @Min(value = 1, message = "{msg.reserva.personas.min}")
    @Schema(description = "Número de personas para la reserva. Debe ser al menos 1.", example = "4")
    private Integer numeroPersonas;

    /**
     * Comentarios opcionales sobre la reserva.
     * <p>
     * - Longitud máxima de 255 caracteres. <br>
     * - Ejemplo: "Mesa cerca de la ventana".
     * </p>
     */
    @Size(max = 255, message = "{msg.reserva.comentarios.size}")
    @Schema(description = "Comentarios opcionales sobre la reserva.", example = "Mesa cerca de la ventana")
    private String comentarios;

    /**
     * ID del restaurante donde se hará la reserva.
     * <p>
     * - No puede ser nulo. <br>
     * - Debe referenciar un restaurante existente. <br>
     * - Ejemplo: "10".
     * </p>
     */
    @NotNull(message = "{msg.reserva.restaurante.notNull}")
    @Schema(description = "ID del restaurante donde se hace la reserva.", example = "10")
    private Long restauranteId;
}
