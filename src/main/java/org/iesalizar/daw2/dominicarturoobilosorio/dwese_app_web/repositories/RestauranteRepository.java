package org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.repositories;

import org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.entities.Restaurante;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestauranteRepository extends JpaRepository<Restaurante, Long> {
    boolean existsByNombre(String nombre);
}
