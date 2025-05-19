package org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.dtos.ListaEsperaCreateDTO;
import org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.dtos.ListaEsperaDTO;
import org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.service.ListaEsperaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Locale;

@Slf4j
@RestController
@RequestMapping("/api/v1/lista-espera")
@RequiredArgsConstructor
public class ListaEsperaController {

    private final ListaEsperaService service;

    @PostMapping
    public ResponseEntity<ListaEsperaDTO> crear(@Valid @RequestBody ListaEsperaCreateDTO dto, Locale locale) {
        return ResponseEntity.ok(service.create(dto, locale));
    }

    @GetMapping
    public ResponseEntity<List<ListaEsperaDTO>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
