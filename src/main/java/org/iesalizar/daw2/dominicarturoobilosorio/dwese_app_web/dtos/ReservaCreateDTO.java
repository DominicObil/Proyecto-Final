package org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * DTO para la creación de reservas en restaurantes.
 *
 * Incluye validaciones JSR-303 y anotaciones OpenAPI para documentación.
 */
@Data
@Schema(name = "ReservaCreateDTO", description = "DTO para crear una reserva en un restaurante.")
public class ReservaCreateDTO {

    @NotNull(message = "{msg.reserva.fecha.notNull}")
    @FutureOrPresent(message = "{msg.reserva.fecha.futureOrPresent}")
    @Schema(description = "Fecha de la reserva. Debe ser hoy o en el futuro.", example = "2025-03-01")
    private LocalDate fechaReserva;

    @NotNull(message = "{msg.reserva.hora.notNull}")
    @Schema(description = "Hora de la reserva.", example = "19:30:00")
    private LocalTime horaReserva;

    @NotNull(message = "{msg.reserva.personas.notNull}")
    @Positive(message = "{msg.reserva.personas.positive}")
    @Min(value = 1, message = "{msg.reserva.personas.min}")
    @Schema(description = "Número de personas para la reserva. Debe ser al menos 1.", example = "4")
    private Integer numeroPersonas;

    @Size(max = 255, message = "{msg.reserva.comentarios.size}")
    @Schema(description = "Comentarios opcionales sobre la reserva.", example = "Mesa cerca de la ventana")
    private String comentarios;

    @NotNull(message = "{msg.reserva.restaurante.notNull}")
    @Schema(description = "ID del restaurante donde se hace la reserva.", example = "10")
    private Long restauranteId;

    /**
     * ID del usuario que realiza la reserva.
     * <p>
     * - Obligatorio. <br>
     * - Corresponde al cliente autenticado. <br>
     * - Ejemplo: "5".
     * </p>
     */
    @NotNull(message = "{msg.reserva.user.notNull}")
    @Schema(description = "ID del cliente que realiza la reserva.", example = "5")
    private Long userId;

    @NotNull(message = "{msg.reserva.turno.notNull}")
    @Schema(description = "ID del turno en el que se desea reservar.", example = "3")
    private Long turnoId;


}
