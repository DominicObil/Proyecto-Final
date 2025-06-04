package org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.mappers;

import org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.dtos.RestauranteCreateDTO;
import org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.dtos.RestauranteDTO;
import org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.entities.Restaurante;
import org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.entities.User;
import org.springframework.stereotype.Component;
@Component
public class RestauranteMapper {

    public RestauranteDTO toDTO(Restaurante restaurante) {
        // Si quieres puedes añadir el ownerId aquí también, si el DTO lo tiene
        return new RestauranteDTO(
                restaurante.getId(),
                restaurante.getNombre(),
                restaurante.getDireccion(),
                restaurante.getTelefono(),
                restaurante.getCapacidad(),
                restaurante.getOwner() != null ? restaurante.getOwner().getId() : null
        );
    }

    // Nuevo: acepta el Owner como parámetro
    public Restaurante toEntity(RestauranteCreateDTO dto, User owner) {
        Restaurante restaurante = new Restaurante(
                dto.getNombre(),
                dto.getDireccion(),
                dto.getTelefono(),
                dto.getCapacidad()
        );
        restaurante.setOwner(owner); // Asignar el owner
        return restaurante;
    }
}
