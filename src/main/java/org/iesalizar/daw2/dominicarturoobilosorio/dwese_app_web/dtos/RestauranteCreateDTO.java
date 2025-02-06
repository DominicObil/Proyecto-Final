package org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.dtos;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class RestauranteCreateDTO {
    @NotEmpty(message = "{msg.restaurante.nombre.notEmpty}")
    @Size(max = 100, message = "{msg.restaurante.nombre.size}")
    private String nombre;

    @NotEmpty(message = "{msg.restaurante.direccion.notEmpty}")
    @Size(max = 200, message = "{msg.restaurante.direccion.size}")
    private String direccion;

    @NotEmpty(message = "{msg.restaurante.telefono.notEmpty}")
    @Pattern(regexp = "^(\\+\\d{1,3}[- ]?)?\\d{10}$", message = "{msg.restaurante.telefono.invalid}")
    private String telefono;

    @NotNull(message = "{msg.restaurante.capacidad.notNull}")
    @Positive(message = "{msg.restaurante.capacidad.positive}")
    private Integer capacidad;
}
