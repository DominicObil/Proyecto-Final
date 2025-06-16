package org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.repositories;

import org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.entities.TurnoMesa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface TurnoMesaRepository extends JpaRepository<TurnoMesa, Long> {
    List<TurnoMesa> findByRestauranteId(Long restauranteId);
    Optional<TurnoMesa> findByHoraInicioAndRestauranteId(LocalTime horaInicio, Long restauranteId);

}
