package org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para la creación de restaurantes en la aplicación.
 *
 * <p>Incluye validaciones JSR-303 como {@code @NotEmpty}, {@code @Size}, {@code @Pattern}, {@code @Positive}.
 * Estas restricciones se reflejan automáticamente en la documentación generada con {@code springdoc-openapi}.
 * </p>
 *
 * @author AMO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "RestauranteCreateDTO", description = "DTO para crear un restaurante en la aplicación.")
public class RestauranteCreateDTO {

    @NotEmpty(message = "{msg.restaurante.nombre.notEmpty}")
    @Size(max = 100, message = "{msg.restaurante.nombre.size}")
    @Schema(description = "Nombre del restaurante.", example = "Restaurante La Mar")
    private String nombre;

    @NotEmpty(message = "{msg.restaurante.direccion.notEmpty}")
    @Size(max = 200, message = "{msg.restaurante.direccion.size}")
    @Schema(description = "Dirección del restaurante.", example = "Av. Principal 123, Ciudad")
    private String direccion;

    @NotEmpty(message = "{msg.restaurante.telefono.notEmpty}")
    @Pattern(regexp = "^(\\+\\d{1,3}[- ]?)?\\d{10}$", message = "{msg.restaurante.telefono.invalid}")
    @Schema(description = "Teléfono del restaurante en formato internacional o nacional.", example = "+34 612345678")
    private String telefono;

    @NotNull(message = "{msg.restaurante.capacidad.notNull}")
    @Positive(message = "{msg.restaurante.capacidad.positive}")
    @Schema(description = "Capacidad máxima de personas en el restaurante.", example = "50")
    private Integer capacidad;

    // 👇 NUEVO: Id del dueño del restaurante
    @NotNull(message = "{msg.restaurante.owner.notNull}")
    @Schema(description = "ID del usuario que es el dueño del restaurante.", example = "42")
    private Long ownerId;
}
