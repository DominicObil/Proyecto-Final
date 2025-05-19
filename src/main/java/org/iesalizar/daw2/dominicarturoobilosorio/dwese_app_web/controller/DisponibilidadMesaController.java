package org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.dtos.DisponibilidadMesaCreateDTO;
import org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.dtos.DisponibilidadMesaDTO;
import org.iesalizar.daw2.dominicarturoobilosorio.dwese_app_web.service.DisponibilidadMesaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/api/v1/disponibilidades")
@RequiredArgsConstructor
public class DisponibilidadMesaController {

    private final DisponibilidadMesaService disponibilidadMesaService;

    @PostMapping
    public ResponseEntity<DisponibilidadMesaDTO> create(@Valid @RequestBody DisponibilidadMesaCreateDTO dto, Locale locale) {
        return ResponseEntity.ok(disponibilidadMesaService.create(dto, locale));
    }

    @GetMapping
    public ResponseEntity<List<DisponibilidadMesaDTO>> getAll() {
        return ResponseEntity.ok(disponibilidadMesaService.getAll());
    }

    @GetMapping("/restaurante/{id}")
    public ResponseEntity<List<DisponibilidadMesaDTO>> getByRestaurante(@PathVariable Long id) {
        return ResponseEntity.ok(disponibilidadMesaService.getByRestaurante(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        disponibilidadMesaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
