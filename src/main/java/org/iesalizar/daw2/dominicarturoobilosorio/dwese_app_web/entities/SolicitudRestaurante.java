package org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "solicitud_restaurante")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SolicitudRestaurante {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String direccion;
    private String telefono;
    private Integer capacidad;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    private LocalDateTime fechaSolicitud = LocalDateTime.now();
}
