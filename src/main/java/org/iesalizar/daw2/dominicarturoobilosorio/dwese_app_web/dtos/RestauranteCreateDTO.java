package org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;

/**
 * DTO para la creación de restaurantes en la aplicación.
 *
 * <p>Incluye validaciones JSR-303 como {@code @NotEmpty}, {@code @Size}, {@code @Pattern}, {@code @Positive}.
 * Estas restricciones se reflejan automáticamente en la documentación generada con {@code springdoc-openapi}.
 * </p>
 *
 * @author [Tu Nombre]
 */
@Data
@Schema(name = "RestauranteCreateDTO", description = "DTO para crear un restaurante en la aplicación.")
public class RestauranteCreateDTO {

    /**
     * Nombre del restaurante.
     * <p>
     * - No puede estar vacío. <br>
     * - Longitud máxima de 100 caracteres. <br>
     * - Ejemplo: "Restaurante La Mar".
     * </p>
     */
    @NotEmpty(message = "{msg.restaurante.nombre.notEmpty}")
    @Size(max = 100, message = "{msg.restaurante.nombre.size}")
    @Schema(description = "Nombre del restaurante.", example = "Restaurante La Mar")
    private String nombre;

    /**
     * Dirección del restaurante.
     * <p>
     * - No puede estar vacía. <br>
     * - Longitud máxima de 200 caracteres. <br>
     * - Ejemplo: "Av. Principal 123, Ciudad".
     * </p>
     */
    @NotEmpty(message = "{msg.restaurante.direccion.notEmpty}")
    @Size(max = 200, message = "{msg.restaurante.direccion.size}")
    @Schema(description = "Dirección del restaurante.", example = "Av. Principal 123, Ciudad")
    private String direccion;

    /**
     * Teléfono de contacto del restaurante.
     * <p>
     * - No puede estar vacío. <br>
     * - Debe cumplir con el formato internacional o nacional. <br>
     * - Ejemplo válido: "+34 612345678" o "612345678".
     * </p>
     */
    @NotEmpty(message = "{msg.restaurante.telefono.notEmpty}")
    @Pattern(regexp = "^(\\+\\d{1,3}[- ]?)?\\d{10}$", message = "{msg.restaurante.telefono.invalid}")
    @Schema(description = "Teléfono del restaurante en formato internacional o nacional.", example = "+34 612345678")
    private String telefono;

    /**
     * Capacidad del restaurante en términos de número de personas.
     * <p>
     * - No puede ser nula. <br>
     * - Debe ser un número positivo mayor que 0. <br>
     * - Ejemplo: "50".
     * </p>
     */
    @NotNull(message = "{msg.restaurante.capacidad.notNull}")
    @Positive(message = "{msg.restaurante.capacidad.positive}")
    @Schema(description = "Capacidad máxima de personas en el restaurante.", example = "50")
    private Integer capacidad;
}
