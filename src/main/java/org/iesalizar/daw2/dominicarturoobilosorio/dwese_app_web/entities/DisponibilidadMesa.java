package org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
    @Table(name = "disponibilidad_mesa")
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class DisponibilidadMesa {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "restaurante_id", nullable = false)
        private Restaurante restaurante;

        @NotNull
        @Column(name = "fecha", nullable = false)
        private LocalDate fecha;

        @NotNull
        @Column(name = "hora_inicio", nullable = false)
        private LocalTime horaInicio;

        @NotNull
        @Column(name = "hora_fin", nullable = false)
        private LocalTime horaFin;

        @NotNull
        @PositiveOrZero
        @Column(name = "mesas_disponibles", nullable = false)
        private Integer mesasDisponibles;
    }


