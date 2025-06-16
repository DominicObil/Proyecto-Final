package org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.repositories;

import org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.entities.Reserva;
import org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.entities.TurnoMesa;
import org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface ReservaRepository extends JpaRepository<Reserva, Long> {


    List<Reserva> findByUser(User user);
    List<Reserva> findByRestauranteId(Long restauranteId);
    List<Reserva> findByRestauranteIdAndFechaReserva(Long restauranteId, LocalDate fechaReserva);

}
