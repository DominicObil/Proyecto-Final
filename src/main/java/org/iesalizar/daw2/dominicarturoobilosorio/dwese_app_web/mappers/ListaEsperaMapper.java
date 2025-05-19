package org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.mappers;

import org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.dtos.ListaEsperaCreateDTO;
import org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.dtos.ListaEsperaDTO;
import org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.entities.ListaEspera;
import org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.entities.Restaurante;
import org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.entities.User;
import org.springframework.stereotype.Component;

@Component
public class ListaEsperaMapper {

    public ListaEsperaDTO toDTO(ListaEspera entity) {
        return new ListaEsperaDTO(
                entity.getId(),
                entity.getUser().getId(),
                entity.getRestaurante().getId(),
                entity.getFecha(),
                entity.getHora(),
                entity.getNumeroPersonas(),
                entity.getEstado().name(),
                entity.getFechaCreacion()
        );
    }

    public ListaEspera toEntity(ListaEsperaCreateDTO dto, User user, Restaurante restaurante) {
        ListaEspera entity = new ListaEspera();
        entity.setFecha(dto.getFecha());
        entity.setHora(dto.getHora());
        entity.setNumeroPersonas(dto.getNumeroPersonas());
        entity.setUser(user);
        entity.setRestaurante(restaurante);
        return entity;
    }

}
