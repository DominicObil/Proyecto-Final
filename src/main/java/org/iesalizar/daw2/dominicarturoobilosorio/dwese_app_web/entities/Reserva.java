package org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * La clase `Reserva` representa una reserva realizada por un cliente.
 * Contiene información sobre la fecha, hora, número de personas y comentarios adicionales.
 */
@Entity
@Table(name = "reservas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reserva {

    // Identificador único de la reserva, clave primaria autogenerada.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Fecha de la reserva, obligatoria.
    @NotNull(message = "{msg.reserva.fecha.notNull}")
    @Column(name = "fecha_reserva", nullable = false)
    private LocalDate fechaReserva;

    // Hora de la reserva, obligatoria.
    @NotNull(message = "{msg.reserva.hora.notNull}")
    @Column(name = "hora_reserva", nullable = false)
    private LocalTime horaReserva;

    // Número de personas para la reserva, obligatorio y debe ser positivo.
    @NotNull(message = "{msg.reserva.personas.notNull}")
    @Positive(message = "{msg.reserva.personas.positive}")
    @Column(name = "numero_personas", nullable = false)
    private Integer numeroPersonas;

    // Comentarios adicionales sobre la reserva, opcional.
    @Size(max = 255, message = "{msg.reserva.comentarios.size}")
    @Column(name = "comentarios", length = 255)
    private String comentarios;

    // Relación muchos a uno con la entidad Restaurante.
    // Una reserva pertenece a un restaurante.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurante_id", nullable = false)
    private Restaurante restaurante;

    /**
     * Constructor sin el campo `id`.
     * Se utiliza para crear reservas antes de ser persistidas en la base de datos.
     *
     * @param fechaReserva    Fecha de la reserva.
     * @param horaReserva     Hora de la reserva.
     * @param numeroPersonas  Número de personas para la reserva.
     * @param comentarios     Comentarios adicionales (opcional).
     * @param restaurante     Restaurante asociado a la reserva.
     */
    public Reserva(LocalDate fechaReserva, LocalTime horaReserva, Integer numeroPersonas, String comentarios, Restaurante restaurante) {
        this.fechaReserva = fechaReserva;
        this.horaReserva = horaReserva;
        this.numeroPersonas = numeroPersonas;
        this.comentarios = comentarios;
        this.restaurante = restaurante;
    }
}
