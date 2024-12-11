package org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.repositories;

import org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.entities.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface ReservaRepository extends JpaRepository<Reserva, Long> {

    // Consulta para obtener todas las reservas de un restaurante por su ID.
    @Query("SELECT r FROM Reserva r WHERE r.restaurante.id = :restauranteId")
    List<Reserva> findByRestauranteId(@Param("restauranteId") Long restauranteId);

    // Consulta para verificar si ya existe una reserva en un restaurante en una fecha y hora específicas.
    @Query("SELECT COUNT(r) > 0 FROM Reserva r WHERE r.restaurante.id = :restauranteId AND r.fechaReserva = :fecha AND r.horaReserva = :hora")
    boolean existsByRestauranteAndFechaHora(@Param("restauranteId") Long restauranteId, @Param("fecha") LocalDate fecha, @Param("hora") LocalTime hora);

    // Consulta para obtener reservas por rango de fechas.
    @Query("SELECT r FROM Reserva r WHERE r.fechaReserva BETWEEN :startDate AND :endDate")
    List<Reserva> findByFechaReservaBetween(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    // Consulta para obtener las reservas por fecha específica.
    @Query("SELECT r FROM Reserva r WHERE r.fechaReserva = :fecha")
    List<Reserva> findByFechaReserva(@Param("fecha") LocalDate fecha);
}
