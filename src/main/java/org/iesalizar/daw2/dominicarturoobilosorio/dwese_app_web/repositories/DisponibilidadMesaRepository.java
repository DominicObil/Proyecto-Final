package org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.repositories;

import org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.entities.DisponibilidadMesa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DisponibilidadMesaRepository extends JpaRepository<DisponibilidadMesa, Long> {
    List<DisponibilidadMesa> findByRestauranteId(Long restauranteId);
}
