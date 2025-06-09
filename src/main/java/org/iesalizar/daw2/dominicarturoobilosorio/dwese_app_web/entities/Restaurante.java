package org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "restaurante")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Restaurante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "{msg.restaurante.nombre.notEmpty}")
    @Size(max = 100, message = "{msg.restaurante.nombre.size}")
    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @NotEmpty(message = "{msg.restaurante.direccion.notEmpty}")
    @Size(max = 200, message = "{msg.restaurante.direccion.size}")
    @Column(name = "direccion", nullable = false, length = 200)
    private String direccion;

    @NotEmpty(message = "{msg.restaurante.telefono.notEmpty}")
    @Pattern(regexp = "^(\\+\\d{1,3}[- ]?)?\\d{10}$", message = "{msg.restaurante.telefono.invalid}")
    @Column(name = "telefono", nullable = false, length = 15)
    private String telefono;

    @NotNull(message = "{msg.restaurante.capacidad.notNull}")
    @Positive(message = "{msg.restaurante.capacidad.positive}")
    @Column(name = "capacidad", nullable = false)
    private Integer capacidad;

    // Relación uno a muchos con reservas
    @OneToMany(mappedBy = "restaurante", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Reserva> reservas;

    // NUEVO: Relación uno a muchos con DisponibilidadMesa
    @OneToMany(mappedBy = "restaurante", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<DisponibilidadMesa> disponibilidadMesas;

    // NUEVO: Relación uno a muchos con TurnoMesa
    @OneToMany(mappedBy = "restaurante", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<TurnoMesa> turnos;

    // NUEVO: Relación uno a muchos con ListaEspera
    @OneToMany(mappedBy = "restaurante", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ListaEspera> listaEspera;

    // imports...
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id") // Puedes cambiar el nombre si quieres
    private User owner;


    public Restaurante(String nombre, String direccion, String telefono, Integer capacidad) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
        this.capacidad = capacidad;

    }
}
