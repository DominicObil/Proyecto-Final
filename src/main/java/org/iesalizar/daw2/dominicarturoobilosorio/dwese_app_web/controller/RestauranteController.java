package org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.controller;

import jakarta.validation.Valid;
import org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.entities.Restaurante;
import org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.repositories.RestauranteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Controller
@RequestMapping("/restaurantes")
public class RestauranteController {

    private static final Logger logger = LoggerFactory.getLogger(RestauranteController.class);

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private MessageSource messageSource;

    // Listar Restaurantes
    @GetMapping
    public String listRestaurantes(Model model) {
        logger.info("Fetching the list of restaurants...");
        List<Restaurante> restaurantes = restauranteRepository.findAll();
        model.addAttribute("restaurantes", restaurantes);
        return "restaurant";  // Cambiar la vista a 'restaurant.html'
    }



    @PreAuthorize("hasRole('ADMIN')")

    // Mostrar formulario para nuevo restaurante
    @GetMapping("/new")
    public String showNewForm(Model model) {
        logger.info("Displaying form to add a new restaurant.");
        model.addAttribute("restaurante", new Restaurante());
        return "restaurant-form";  // Cambiar la vista a 'restaurant-form.html'
    }







    @PreAuthorize("hasRole('ADMIN')")

    // Guardar restaurante
    @PostMapping("/save")
    public String saveRestaurante(@Valid @ModelAttribute("restaurante") Restaurante restaurante,
                                  BindingResult result,
                                  RedirectAttributes redirectAttributes,
                                  Locale locale) {
        logger.info("Guardando el restaurante con nombre {}", restaurante.getNombre());

        // Verificar si hay errores de validación
        if (result.hasErrors()) {
            return "restaurant-form";  // Devuelve el formulario para mostrar los errores de validación
        }

        // Verificar si el restaurante ya existe por nombre
        if (restauranteRepository.existsByNombre(restaurante.getNombre())) {
            logger.warn("El restaurante con nombre '{}' ya existe.", restaurante.getNombre());
            String errorMessage = messageSource.getMessage("msg.restaurant-controller.insert.nameExist", null, locale);
            redirectAttributes.addFlashAttribute("errorMessage", errorMessage);
            return "redirect:/restaurantes/new"; // Redirigir al formulario para crear un nuevo restaurante
        }

        // Guardar el restaurante
        restauranteRepository.save(restaurante);
        logger.info("Restaurante '{}' guardado con éxito.", restaurante.getNombre());
        return "redirect:/restaurantes"; // Redirigir a la lista de restaurantes
    }


    @PreAuthorize("hasRole('ADMIN')")

    // Mostrar formulario para editar restaurante
    @GetMapping("/edit")
    public String showEditForm(@RequestParam("id") Long id, Model model, RedirectAttributes redirectAttributes) {
        logger.info("Displaying edit form for restaurant with ID {}", id);
        Optional<Restaurante> restauranteOptional = restauranteRepository.findById(id);

        if (restauranteOptional.isEmpty()) {
            logger.warn("Restaurant with ID {} not found.", id);
            redirectAttributes.addFlashAttribute("errorMessage", "Restaurante no encontrado.");
            return "redirect:/restaurantes";
        }

        model.addAttribute("restaurante", restauranteOptional.get());
        return "restaurant-form";  // Cambiar la vista a 'restaurant-form.html' para editar
    }


    @PreAuthorize("hasRole('ADMIN')")

    @PostMapping("/delete")
    public String deleteRestaurante(@RequestParam("id") Long id, RedirectAttributes redirectAttributes) {
        logger.info("Eliminando restaurante con ID {}", id);
        restauranteRepository.deleteById(id); // Eliminar el restaurante
        logger.info("Restaurante con ID {} eliminado con éxito.", id);
        return "redirect:/restaurantes"; // Redirigir a la lista de restaurantes
    }



    @PreAuthorize("hasRole('ADMIN')")

    @PostMapping("/insert")
    public String insertRestaurante(
            @Valid @ModelAttribute("restaurante") Restaurante restaurante,
            BindingResult result,
            RedirectAttributes redirectAttributes,
            Locale locale) {

        // Si hay errores de validación, volvemos a mostrar el formulario
        if (result.hasErrors()) {
            return "restaurant-form";  // Ajusta el nombre de la vista si es necesario
        }

        logger.info("Inserting new restaurant with name {}", restaurante.getNombre());

        // Verificamos si ya existe un restaurante con ese nombre
        if (restauranteRepository.existsByNombre(restaurante.getNombre())) {
            logger.warn("Restaurant name {} already exists.", restaurante.getNombre());

            // Obtenemos el mensaje de error para mostrar
            String errorMessage = messageSource.getMessage("msg.restaurant-controller.insert.nameExist", null, locale);
            redirectAttributes.addFlashAttribute("errorMessage", errorMessage);

            // Redirigimos de nuevo al formulario de creación
            return "redirect:/restaurantes/new";  // Ajusta la URL según tu ruta de creación del restaurante
        }

        // Guardamos el restaurante si no existe
        restauranteRepository.save(restaurante);
        logger.info("Restaurant {} inserted successfully.", restaurante.getNombre());

        // Redirigimos a la lista de restaurantes
        return "redirect:/restaurantes";
    }




}
