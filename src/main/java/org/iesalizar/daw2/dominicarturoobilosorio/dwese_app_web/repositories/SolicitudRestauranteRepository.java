package org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.repositories;

import org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.entities.SolicitudRestaurante;
import org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SolicitudRestauranteRepository extends JpaRepository<SolicitudRestaurante, Long> {
    // Puedes añadir métodos custom si necesitas, por ejemplo: findByOwner, findByEstado, etc

}

