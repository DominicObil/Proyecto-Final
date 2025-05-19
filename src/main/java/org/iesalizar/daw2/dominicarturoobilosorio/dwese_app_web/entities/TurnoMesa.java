package org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.entities.Restaurante;

import java.time.LocalTime;

@Entity
@Table(name = "turno_mesa")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TurnoMesa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurante_id", nullable = false)
    private Restaurante restaurante;

    @NotEmpty
    @Column(name = "nombre_turno", nullable = false, length = 50)
    private String nombreTurno;

    @NotNull
    @Column(name = "hora_inicio", nullable = false)
    private LocalTime horaInicio;

    @NotNull
    @Column(name = "hora_fin", nullable = false)
    private LocalTime horaFin;
}
