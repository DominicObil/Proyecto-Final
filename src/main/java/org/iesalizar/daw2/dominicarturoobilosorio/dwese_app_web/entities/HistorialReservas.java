package org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.enums.AccionReserva;

import java.time.LocalDateTime;

@Entity
@Table(name = "HistorialReservas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HistorialReservas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reserva_id", nullable = false)
    private Reserva reserva;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AccionReserva accion;

    @Column(name = "fecha", nullable = false)
    private LocalDateTime fecha;

    @PrePersist
    public void onCreate() {
        this.fecha = LocalDateTime.now();
    }
}

