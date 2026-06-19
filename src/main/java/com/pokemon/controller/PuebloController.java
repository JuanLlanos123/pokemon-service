package com.pokemon.controller;

import com.pokemon.entity.Pueblo;
import com.pokemon.service.PuebloService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/pueblos")
@Tag(name = "Pueblos", description = "Endpoints para la gestión de pueblos de origen de los entrenadores")
public class PuebloController {

    private final PuebloService puebloService;

    public PuebloController(PuebloService puebloService) {
        this.puebloService = puebloService;
    }

    @GetMapping
    @Operation(summary = "Obtener todos los pueblos", description = "Retorna una lista de todos los pueblos registrados.")
    public ResponseEntity<List<Pueblo>> obtenerTodos() {
        return ResponseEntity.ok(puebloService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener un pueblo por su ID", description = "Busca y retorna un pueblo específico por su identificador numérico.")
    public ResponseEntity<Pueblo> obtenerPorId(@PathVariable Integer id) {
        return puebloService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/uuid/{uuid}")
    @Operation(summary = "Obtener un pueblo por su UUID", description = "Busca y retorna un pueblo específico por su identificador UUID único.")
    public ResponseEntity<Pueblo> obtenerPorUuid(@PathVariable UUID uuid) {
        return puebloService.findByUuid(uuid)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Crear un nuevo pueblo", description = "Registra un nuevo pueblo en la base de datos.")
    public ResponseEntity<Pueblo> crear(@RequestBody Pueblo pueblo) {
        return ResponseEntity.status(HttpStatus.CREATED).body(puebloService.save(pueblo));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un pueblo por su ID", description = "Elimina un pueblo específico de la base de datos.")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        if (puebloService.findById(id).isPresent()) {
            puebloService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
