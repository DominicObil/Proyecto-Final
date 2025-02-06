package org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.service;

import org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.dtos.ReservaCreateDTO;
import org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.dtos.ReservaDTO;
import org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.entities.Reserva;
import org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.entities.Restaurante;
import org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.mappers.ReservaMapper;
import org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.repositories.ReservaRepository;
import org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.repositories.RestauranteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReservaService {

    private static final Logger logger = LoggerFactory.getLogger(ReservaService.class);

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private ReservaMapper reservaMapper;

    @Autowired
    private MessageSource messageSource;

    /**
     * Obtiene todas las reservas.
     *
     * @return Lista de ReservaDTO.
     */
    public List<ReservaDTO> getAllReservas() {
        logger.info("Solicitando todas las reservas...");
        try {
            List<Reserva> reservas = reservaRepository.findAll();
            logger.info("Se han encontrado {} reservas.", reservas.size());
            return reservas.stream()
                    .map(reservaMapper::toDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Error al obtener la lista de reservas: {}", e.getMessage());
            throw e;
        }
    }

    /**
     * Obtiene una reserva por su ID.
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

    /**
     * Crea una nueva reserva en la base de datos.
     *
     * @param reservaCreateDTO DTO con los datos de la reserva a crear.
     * @param locale Idioma para los mensajes de error.
     * @return DTO de la reserva creada.
     * @throws IllegalArgumentException Si el restaurante no existe.
     */
    public ReservaDTO createReserva(ReservaCreateDTO reservaCreateDTO, Locale locale) {
        logger.info("Creando una nueva reserva...");

        Restaurante restaurante = restauranteRepository.findById(reservaCreateDTO.getRestauranteId())
                .orElseThrow(() -> {
                    String errorMessage = messageSource.getMessage("msg.reserva.restaurante.notFound", null, locale);
                    logger.warn("Error al crear reserva: {}", errorMessage);
                    return new IllegalArgumentException(errorMessage);
                });

        Reserva reserva = reservaMapper.toEntity(reservaCreateDTO);
        reserva.setRestaurante(restaurante);

        Reserva savedReserva = reservaRepository.save(reserva);
        logger.info("Reserva creada exitosamente con ID {}", savedReserva.getId());
        return reservaMapper.toDTO(savedReserva);
    }

    /**
     * Actualiza una reserva existente en la base de datos.
     *
     * @param id Identificador de la reserva a actualizar.
     * @param reservaCreateDTO DTO con los nuevos datos de la reserva.
     * @param locale Idioma para los mensajes de error.
     * @return DTO de la reserva actualizada.
     * @throws IllegalArgumentException Si la reserva no existe o el restaurante no es válido.
     */
    public ReservaDTO updateReserva(Long id, ReservaCreateDTO reservaCreateDTO, Locale locale) {
        logger.info("Actualizando reserva con ID {}", id);

        Reserva existingReserva = reservaRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("No se encontró la reserva con ID {}", id);
                    return new IllegalArgumentException("La reserva no existe.");
                });

        Restaurante restaurante = restauranteRepository.findById(reservaCreateDTO.getRestauranteId())
                .orElseThrow(() -> {
                    String errorMessage = messageSource.getMessage("msg.reserva.restaurante.notFound", null, locale);
                    logger.warn("Error al actualizar reserva: {}", errorMessage);
                    return new IllegalArgumentException(errorMessage);
                });

        existingReserva.setFechaReserva(reservaCreateDTO.getFechaReserva());
        existingReserva.setHoraReserva(reservaCreateDTO.getHoraReserva());
        existingReserva.setNumeroPersonas(reservaCreateDTO.getNumeroPersonas());
        existingReserva.setComentarios(reservaCreateDTO.getComentarios());
        existingReserva.setRestaurante(restaurante);

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
