package org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * La clase `Restaurante` representa un restaurante dentro del sistema.
 * Incluye información como el nombre, la dirección, el teléfono y su capacidad máxima.
 */
@Entity
@Table(name = "Restaurante")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Restaurante {

    // Identificador único del restaurante, clave primaria autogenerada.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Nombre del restaurante, obligatorio y con un máximo de 100 caracteres.
    @NotEmpty(message = "{msg.restaurante.nombre.notEmpty}")
    @Size(max = 100, message = "{msg.restaurante.nombre.size}")
    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    // Dirección del restaurante, obligatoria y con un máximo de 200 caracteres.
    @NotEmpty(message = "{msg.restaurante.direccion.notEmpty}")
    @Size(max = 200, message = "{msg.restaurante.direccion.size}")
    @Column(name = "direccion", nullable = false, length = 200)
    private String direccion;

    // Teléfono del restaurante, obligatorio y con validación de formato.
    @NotEmpty(message = "{msg.restaurante.telefono.notEmpty}")
    @Pattern(regexp = "^(\\+\\d{1,3}[- ]?)?\\d{10}$", message = "{msg.restaurante.telefono.invalid}")
    @Column(name = "telefono", nullable = false, length = 15)
    private String telefono;

    // Capacidad máxima del restaurante, obligatorio y debe ser positivo.
    @NotNull(message = "{msg.restaurante.capacidad.notNull}")
    @Positive(message = "{msg.restaurante.capacidad.positive}")
    @Column(name = "capacidad", nullable = false)
    private Integer capacidad;

    // Relación uno a muchos con la entidad Reserva.
    // Un restaurante puede tener muchas reservas.
    @OneToMany(mappedBy = "restaurante", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Reserva> reservas;

    /**
     * Constructor sin el campo `id`.
     * Se utiliza para instanciar restaurantes antes de ser persistidos en la base de datos.
     *
     * @param nombre    Nombre del restaurante.
     * @param direccion Dirección del restaurante.
     * @param telefono  Teléfono del restaurante.
     * @param capacidad Capacidad del restaurante.
     */
    public Restaurante(String nombre, String direccion, String telefono, Integer capacidad) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
        this.capacidad = capacidad;
    }
}
