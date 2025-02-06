package org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor  // ⬅️ Esto añade el constructor necesario
public class RestauranteDTO {
    private Long id;
    private String nombre;
    private String direccion;
    private String telefono;
    private Integer capacidad;
}
