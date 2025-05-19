package org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.repositories;

import org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.entities.TurnoMesa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TurnoMesaRepository extends JpaRepository<TurnoMesa, Long> {
    List<TurnoMesa> findByRestauranteId(Long restauranteId);
}
