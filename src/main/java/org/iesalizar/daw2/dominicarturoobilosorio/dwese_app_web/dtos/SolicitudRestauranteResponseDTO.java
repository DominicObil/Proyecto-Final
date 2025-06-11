package org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * DTO para mostrar solicitudes de restaurante.
 */
@Data
@Schema(name = "SolicitudRestauranteResponseDTO", description = "DTO para devolver información de una solicitud de restaurante.")
public class SolicitudRestauranteResponseDTO {

    @Schema(description = "ID de la solicitud.", example = "1")
    private Long id;

    @Schema(description = "Nombre del restaurante propuesto.", example = "Pizzería El Buen Gusto")
    private String nombre;

    @Schema(description = "Dirección del restaurante propuesto.", example = "Calle Falsa 123, Sevilla")
    private String direccion;

    @Schema(description = "Teléfono de contacto.", example = "954112233")
    private String telefono;

    @Schema(description = "Capacidad máxima del restaurante.", example = "45")
    private Integer capacidad;

    @Schema(description = "Nombre de usuario del propietario que solicita.", example = "nuevoowner")
    private String ownerUsername;

    @Schema(description = "ID del propietario que solicita.", example = "5")
    private Long ownerId;

    @Schema(description = "Fecha y hora en que se creó la solicitud.", example = "2025-06-11T21:03:54.123")
    private LocalDateTime fechaSolicitud;

}
