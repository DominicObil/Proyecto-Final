package org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.controller;

import jakarta.validation.Valid;
import org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.entities.Reserva;
import org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.repositories.RestauranteRepository;
import org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.repositories.ReservaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Controller
@RequestMapping("/reservas")
public class ReservaController {

    private static final Logger logger = LoggerFactory.getLogger(ReservaController.class);

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private RestauranteRepository restauranteRepository;

    @GetMapping
    public String listReservas(Model model) {
        logger.info("Fetching the list of reservations...");
        List<Reserva> reservas = reservaRepository.findAll();
        model.addAttribute("reservas", reservas);
        return "reservations";
    }

    @GetMapping("/new")
    public String showNewForm(Model model) {
        logger.info("Displaying form to add a new reservation.");
        model.addAttribute("reserva", new Reserva());
        model.addAttribute("restaurantes", restauranteRepository.findAll());
        return "reservation-form";  // Cambiado a 'reservation-form'
    }

    @PostMapping("/save")
    public String saveReserva(@Valid @ModelAttribute("reserva") Reserva reserva, BindingResult result) {
        if (result.hasErrors()) {
            return "reservation-form";  // Cambiado a 'reservation-form'
        }

        reservaRepository.save(reserva);
        return "redirect:/reservas";  // Redireccionado a '/reservas' en lugar de 'reservations'
    }
}