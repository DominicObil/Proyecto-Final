package org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.mappers;

import org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.dtos.RestauranteCreateDTO;
import org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.dtos.RestauranteDTO;
import org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.entities.Restaurante;
import org.springframework.stereotype.Component;

@Component
public class RestauranteMapper {

    public RestauranteDTO toDTO(Restaurante restaurante) {
        return new RestauranteDTO(
                restaurante.getId(),
                restaurante.getNombre(),
                restaurante.getDireccion(),
                restaurante.getTelefono(),
                restaurante.getCapacidad()
        );
    }

    public Restaurante toEntity(RestauranteCreateDTO dto) {
        return new Restaurante(dto.getNombre(), dto.getDireccion(), dto.getTelefono(), dto.getCapacidad());
    }
}
