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

    /**
     * Obtiene todas las reservas con paginación y las convierte en una página de ReservaDTO.
     *
     * @param pageable Objeto de paginación que define la página, el tamaño y la ordenación.
     * @return Página de ReservaDTO.
     */
    public Page<ReservaDTO> getAllReservas(Pageable pageable) {
        logger.info("Solicitando todas las reservas con paginación: página {}, tamaño {}",
                pageable.getPageNumber(), pageable.getPageSize());

        try {
            Page<Reserva> reservas = reservaRepository.findAll(pageable);
            logger.info("Se han encontrado {} reservas en la página actual.", reservas.getNumberOfElements());
            return reservas.map(reservaMapper::toDTO);
        } catch (Exception e) {
            logger.error("Error al obtener la lista de reservas: {}", e.getMessage());
            throw new RuntimeException("Error al obtener las reservas", e);
        }
    }


    /**
     * Obtiene una reserva por su ID y la convierte en un ReservaDTO.
     *
     * @param id Identificador único de la reserva.
     * @return ReservaDTO de la reserva encontrada.
     * @throws IllegalArgumentException Si la reserva no existe.
     */
    public ReservaDTO getReservaById(Long id) {
        logger.info("Buscando reserva con ID {}", id);
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("No se encontró la reserva con ID {}", id);
                    return new IllegalArgumentException("La reserva no existe.");
                });
        logger.info("Reserva con ID {} encontrada.", id);
        return reservaMapper.toDTO(reserva);
    }

    public List<ReservaDTO> getReservasPorRestaurante(Long restauranteId) {
        List<Reserva> reservas = reservaRepository.findByRestauranteId(restauranteId);
        return reservas.stream().map(reservaMapper::toDTO).toList();
    }


    /**
     * Crea una nueva reserva en la base de datos.
     *
     * @param reservaCreateDTO DTO con los datos de la reserva a crear (sin userId).
     * @param locale Idioma para los mensajes de error.
     * @param user El usuario autenticado (del token).
     * @return DTO de la reserva creada.
     * @throws IllegalArgumentException Si el restaurante no existe.
     */
    public ReservaDTO createReserva(ReservaCreateDTO reservaCreateDTO, Locale locale, User user) {
        logger.info("Creando una nueva reserva...");

        // Buscar restaurante
        Restaurante restaurante = restauranteRepository.findById(reservaCreateDTO.getRestauranteId())
                .orElseThrow(() -> {
                    String errorMessage = messageSource.getMessage("msg.reserva.restaurante.notFound", null, locale);
                    logger.warn("Error al crear reserva: {}", errorMessage);
                    return new IllegalArgumentException(errorMessage);
                });

        // Buscar turno y validar que pertenezca al restaurante
        TurnoMesa turno = turnoMesaRepository.findById(reservaCreateDTO.getTurnoId())
                .filter(t -> t.getRestaurante().getId().equals(restaurante.getId()))
                .orElseThrow(() -> {
                    String errorMessage = messageSource.getMessage("msg.reserva.turno.invalid", null, locale);
                    logger.warn("Error al crear reserva: {}", errorMessage);
                    return new IllegalArgumentException(errorMessage);
                });

        // Mapear DTO a entidad con restaurante y user
        Reserva reserva = reservaMapper.toEntity(reservaCreateDTO, user, restaurante, turno);

        // Guardar reserva
        Reserva savedReserva = reservaRepository.save(reserva);
        logger.info("Reserva creada exitosamente con ID {}", savedReserva.getId());

        // Retornar DTO de salida
        return reservaMapper.toDTO(savedReserva);
    }

    /**
     * Actualiza una reserva existente en la base de datos.
     *
     * @param id Identificador de la reserva a actualizar.
     * @param reservaCreateDTO DTO con los nuevos datos de la reserva (sin userId).
     * @param locale Idioma para los mensajes de error.
     * @param user El usuario autenticado (del token).
     * @return DTO de la reserva actualizada.
     * @throws IllegalArgumentException Si la reserva no existe o el restaurante no es válido.
     */
    public ReservaDTO updateReserva(Long id, ReservaCreateDTO reservaCreateDTO, Locale locale, User user) {
        logger.info("Actualizando reserva con ID {}", id);

        // Buscar la reserva existente
        Reserva existingReserva = reservaRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("No se encontró la reserva con ID {}", id);
                    return new IllegalArgumentException("La reserva no existe.");
                });

        // Buscar el restaurante
        Restaurante restaurante = restauranteRepository.findById(reservaCreateDTO.getRestauranteId())
                .orElseThrow(() -> {
                    String errorMessage = messageSource.getMessage("msg.reserva.restaurante.notFound", null, locale);
                    logger.warn("Error al actualizar reserva: {}", errorMessage);
                    return new IllegalArgumentException(errorMessage);
                });

        // Buscar el turno y validar que sea del restaurante
        TurnoMesa turno = turnoMesaRepository.findById(reservaCreateDTO.getTurnoId())
                .filter(t -> t.getRestaurante().getId().equals(restaurante.getId()))
                .orElseThrow(() -> {
                    String errorMessage = messageSource.getMessage("msg.reserva.turno.invalid", null, locale);
                    logger.warn("Error al actualizar reserva: {}", errorMessage);
                    return new IllegalArgumentException(errorMessage);
                });

        // Actualizar campos
        existingReserva.setFechaReserva(reservaCreateDTO.getFechaReserva());
        existingReserva.setHoraReserva(reservaCreateDTO.getHoraReserva());
        existingReserva.setNumeroPersonas(reservaCreateDTO.getNumeroPersonas());
        existingReserva.setComentarios(reservaCreateDTO.getComentarios());
        existingReserva.setRestaurante(restaurante);
        existingReserva.setUser(user); // Usa el usuario autenticado recibido
        existingReserva.setTurno(turno);

        // Guardar y devolver
        Reserva updatedReserva = reservaRepository.save(existingReserva);
        logger.info("Reserva con ID {} actualizada exitosamente.", id);

        return reservaMapper.toDTO(updatedReserva);
    }

    /**
     * Elimina una reserva por su ID.
     *
     * @param id Identificador único de la reserva.
     * @throws IllegalArgumentException Si la reserva no existe.
     */
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
