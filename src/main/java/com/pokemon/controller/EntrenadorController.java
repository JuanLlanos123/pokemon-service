package com.pokemon.controller;

import com.pokemon.entity.Entrenador;
import com.pokemon.service.EntrenadorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/entrenadores")
@Tag(name = "Entrenadores", description = "Endpoints para la gestión de entrenadores y el control de capturas de Pokémon")
public class EntrenadorController {

    private final EntrenadorService entrenadorService;

    public EntrenadorController(EntrenadorService entrenadorService) {
        this.entrenadorService = entrenadorService;
    }

    @GetMapping
    @Operation(summary = "Obtener todos los entrenadores", description = "Retorna una lista de todos los entrenadores registrados.")
    public ResponseEntity<List<Entrenador>> obtenerTodos() {
        return ResponseEntity.ok(entrenadorService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener un entrenador por su ID", description = "Busca y retorna un entrenador específico por su identificador numérico.")
    public ResponseEntity<Entrenador> obtenerPorId(@PathVariable Integer id) {
        return entrenadorService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/uuid/{uuid}")
    @Operation(summary = "Obtener un entrenador por su UUID", description = "Busca y retorna un entrenador específico por su identificador UUID único.")
    public ResponseEntity<Entrenador> obtenerPorUuid(@PathVariable UUID uuid) {
        return entrenadorService.findByUuid(uuid)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Crear un nuevo entrenador", description = "Registra un nuevo entrenador en la base de datos.")
    public ResponseEntity<Entrenador> crear(@RequestBody Entrenador entrenador) {
        return ResponseEntity.status(HttpStatus.CREATED).body(entrenadorService.save(entrenador));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un entrenador por su ID", description = "Elimina un entrenador específico de la base de datos.")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        if (entrenadorService.findById(id).isPresent()) {
            entrenadorService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/{entrenadorId}/capturar/{pokemonId}")
    @Operation(summary = "Capturar un Pokémon", description = "Asocia un Pokémon capturado a un entrenador.")
    public ResponseEntity<Void> capturar(@PathVariable Integer entrenadorId, @PathVariable Integer pokemonId) {
        try {
            entrenadorService.capturarPokemon(entrenadorId, pokemonId);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/{entrenadorId}/liberar/{pokemonId}")
    @Operation(summary = "Liberar un Pokémon", description = "Desasocia un Pokémon de un entrenador.")
    public ResponseEntity<Void> liberar(@PathVariable Integer entrenadorId, @PathVariable Integer pokemonId) {
        try {
            entrenadorService.liberarPokemon(entrenadorId, pokemonId);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
