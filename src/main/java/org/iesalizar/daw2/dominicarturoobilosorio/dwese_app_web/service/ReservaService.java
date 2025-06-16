package org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.service;

import org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.dtos.ReservaCreateDTO;
import org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.dtos.ReservaDTO;
import org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.entities.Reserva;
import org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.entities.Restaurante;
import org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.entities.TurnoMesa;
import org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.entities.User;
import org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.mappers.ReservaMapper;
import org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.repositories.ReservaRepository;
import org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.repositories.UserRepository;
import org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.repositories.RestauranteRepository;
import org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.repositories.TurnoMesaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
@Service
public class ReservaService {

    private static final Logger logger = LoggerFactory.getLogger(ReservaService.class);

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TurnoMesaRepository turnoMesaRepository;

    @Autowired
    private ReservaMapper reservaMapper;

    @Autowired
    private MessageSource messageSource;

    public Page<ReservaDTO> getAllReservas(Pageable pageable) {
        logger.info("Solicitando todas las reservas con paginación: página {}, tamaño {}", pageable.getPageNumber(), pageable.getPageSize());
        try {
            Page<Reserva> reservas = reservaRepository.findAll(pageable);
            logger.info("Se han encontrado {} reservas en la página actual.", reservas.getNumberOfElements());
            return reservas.map(reservaMapper::toDTO);
        } catch (Exception e) {
            logger.error("Error al obtener la lista de reservas: {}", e.getMessage());
            throw new RuntimeException("Error al obtener las reservas", e);
        }
    }

    public ReservaDTO getReservaById(Long id) {
        logger.info("Buscando reserva con ID {}", id);
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("No se encontró la reserva con ID {}", id);
                    return new IllegalArgumentException("La reserva no existe.");
                });
        return reservaMapper.toDTO(reserva);
    }

    public List<ReservaDTO> getReservasPorRestaurante(Long restauranteId) {
        List<Reserva> reservas = reservaRepository.findByRestauranteId(restauranteId);
        return reservas.stream().map(reservaMapper::toDTO).toList();
    }

    public ReservaDTO createReserva(ReservaCreateDTO reservaCreateDTO, Locale locale, User user) {
        logger.info("Creando una nueva reserva...");

        Restaurante restaurante = restauranteRepository.findById(reservaCreateDTO.getRestauranteId())
                .orElseThrow(() -> {
                    String errorMessage = messageSource.getMessage("msg.reserva.restaurante.notFound", null, locale);
                    logger.warn("Error al crear reserva: {}", errorMessage);
                    return new IllegalArgumentException(errorMessage);
                });

        TurnoMesa turno = null;
        if (reservaCreateDTO.getTurnoId() != null) {
            turno = turnoMesaRepository.findById(reservaCreateDTO.getTurnoId())
                    .filter(t -> t.getRestaurante().getId().equals(restaurante.getId()))
                    .orElseThrow(() -> {
                        String errorMessage = messageSource.getMessage("msg.reserva.turno.invalid", null, locale);
                        logger.warn("Error al crear reserva: {}", errorMessage);
                        return new IllegalArgumentException(errorMessage);
                    });
        }

        Reserva reserva = reservaMapper.toEntity(reservaCreateDTO, user, restaurante, turno);
        Reserva savedReserva = reservaRepository.save(reserva);

        logger.info("Reserva creada exitosamente con ID {}", savedReserva.getId());
        return reservaMapper.toDTO(savedReserva);
    }

    public ReservaDTO updateReserva(Long id, ReservaCreateDTO reservaCreateDTO, Locale locale, User user) {
        logger.info("Actualizando reserva con ID {}", id);

        Reserva existingReserva = reservaRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("No se encontró la reserva con ID {}", id);
                    return new IllegalArgumentException("La reserva no existe.");
                });

        // 👇 VALIDACIÓN: solo el creador o el owner del restaurante pueden modificar
        if (!existingReserva.getUser().getId().equals(user.getId()) &&
                !existingReserva.getRestaurante().getOwner().getId().equals(user.getId())) {
            logger.warn("Usuario no autorizado a modificar la reserva ID {}", id);
            throw new SecurityException("No estás autorizado para modificar esta reserva.");
        }

        Restaurante restaurante = restauranteRepository.findById(reservaCreateDTO.getRestauranteId())
                .orElseThrow(() -> {
                    String errorMessage = messageSource.getMessage("msg.reserva.restaurante.notFound", null, locale);
                    logger.warn("Error al actualizar reserva: {}", errorMessage);
                    return new IllegalArgumentException(errorMessage);
                });

        TurnoMesa turno = null;
        if (reservaCreateDTO.getTurnoId() != null) {
            turno = turnoMesaRepository.findById(reservaCreateDTO.getTurnoId())
                    .filter(t -> t.getRestaurante().getId().equals(restaurante.getId()))
                    .orElseThrow(() -> {
                        String errorMessage = messageSource.getMessage("msg.reserva.turno.invalid", null, locale);
                        logger.warn("Error al actualizar reserva: {}", errorMessage);
                        return new IllegalArgumentException(errorMessage);
                    });
        }

        // Actualizar solo los campos modificables
        existingReserva.setFechaReserva(reservaCreateDTO.getFechaReserva());
        existingReserva.setHoraReserva(reservaCreateDTO.getHoraReserva());
        existingReserva.setNumeroPersonas(reservaCreateDTO.getNumeroPersonas());
        existingReserva.setComentarios(reservaCreateDTO.getComentarios());
        existingReserva.setRestaurante(restaurante);
        existingReserva.setTurno(turno);

        // ❌ NO cambies el user original:
        // existingReserva.setUser(user);

        Reserva updatedReserva = reservaRepository.save(existingReserva);
        logger.info("Reserva con ID {} actualizada exitosamente.", id);

        return reservaMapper.toDTO(updatedReserva);
    }


    public List<ReservaDTO> getReservasPorRestauranteYFecha(Long restauranteId, LocalDate fecha) {
        List<Reserva> reservas = reservaRepository.findByRestauranteIdAndFechaReserva(restauranteId, fecha);
        return reservas.stream().map(reservaMapper::toDTO).toList();
    }

    public void deleteReserva(Long id) {
        logger.info("Buscando reserva con ID {}", id);
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("No se encontró la reserva con ID {}", id);
                    return new IllegalArgumentException("La reserva no existe.");
                });

        reservaRepository.deleteById(id);
        logger.info("Reserva con ID {} eliminada exitosamente.", id);
    }
}
