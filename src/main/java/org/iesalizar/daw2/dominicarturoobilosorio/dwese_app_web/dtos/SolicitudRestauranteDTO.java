package org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.dtos;

import lombok.Data;

@Data
public class SolicitudRestauranteDTO {
    private String nombre;
    private String direccion;
    private String telefono;
    private Integer capacidad;

}
