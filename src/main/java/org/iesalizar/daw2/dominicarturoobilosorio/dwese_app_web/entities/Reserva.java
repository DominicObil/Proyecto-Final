package org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.entities;


import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.enums.EstadoReserva;

@Entity
@Table(name = "reserva")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "{msg.reserva.fecha.notNull}")
    @Column(name = "fecha_reserva", nullable = false)
    private LocalDate fechaReserva;

    @NotNull(message = "{msg.reserva.hora.notNull}")
    @Column(name = "hora_reserva", nullable = false)
    private LocalTime horaReserva;

    @NotNull(message = "{msg.reserva.personas.notNull}")
    @Positive(message = "{msg.reserva.personas.positive}")
    @Column(name = "numero_personas", nullable = false)
    private Integer numeroPersonas;

    @Size(max = 255, message = "{msg.reserva.comentarios.size}")
    @Column(name = "comentarios", length = 255)
    private String comentarios;

    // NUEVO: Relación con Restaurante
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurante_id", nullable = false)
    private Restaurante restaurante;

    // NUEVO: Relación con Usuario que realiza la reserva
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // NUEVO: Estado de la reserva
    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private EstadoReserva estado = EstadoReserva.PENDIENTE;

    // NUEVO: Fecha de creación automática
    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    private LocalDateTime fechaCreacion;

    @PrePersist
    protected void onCreate() {
        this.fechaCreacion = LocalDateTime.now();


    }
    @ManyToOne(optional = true)
    @JoinColumn(name = "turno_id")
    private TurnoMesa turno;


    public Reserva(LocalDate fechaReserva, LocalTime horaReserva, Integer numeroPersonas, String comentarios, Restaurante restaurante, User user) {
        this.fechaReserva = fechaReserva;
        this.horaReserva = horaReserva;
        this.numeroPersonas = numeroPersonas;
        this.comentarios = comentarios;
        this.restaurante = restaurante;
        this.user = user;
        this.estado = EstadoReserva.PENDIENTE;
    }
}
