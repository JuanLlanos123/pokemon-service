package com.pokemon.controller;

import com.pokemon.entity.TipoPokemon;
import com.pokemon.service.TipoPokemonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/tipos-pokemon")
@Tag(name = "Tipos de Pokémon", description = "Endpoints para la gestión de tipos de Pokémon (Fuego, Agua, Planta, etc.)")
public class TipoPokemonController {

    private final TipoPokemonService tipoPokemonService;

    public TipoPokemonController(TipoPokemonService tipoPokemonService) {
        this.tipoPokemonService = tipoPokemonService;
    }

    @GetMapping
    @Operation(summary = "Obtener todos los tipos de Pokémon", description = "Retorna una lista de todos los tipos registrados.")
    public ResponseEntity<List<TipoPokemon>> obtenerTodos() {
        return ResponseEntity.ok(tipoPokemonService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener un tipo de Pokémon por su ID", description = "Busca y retorna un tipo específico por su identificador numérico.")
    public ResponseEntity<TipoPokemon> obtenerPorId(@PathVariable Integer id) {
        return tipoPokemonService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/uuid/{uuid}")
    @Operation(summary = "Obtener un tipo de Pokémon por su UUID", description = "Busca y retorna un tipo específico por su identificador UUID único.")
    public ResponseEntity<TipoPokemon> obtenerPorUuid(@PathVariable UUID uuid) {
        return tipoPokemonService.findByUuid(uuid)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Crear un nuevo tipo de Pokémon", description = "Registra un nuevo tipo en la base de datos.")
    public ResponseEntity<TipoPokemon> crear(@RequestBody TipoPokemon tipoPokemon) {
        return ResponseEntity.status(HttpStatus.CREATED).body(tipoPokemonService.save(tipoPokemon));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un tipo de Pokémon por su ID", description = "Elimina un tipo de Pokémon de la base de datos.")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        if (tipoPokemonService.findById(id).isPresent()) {
            tipoPokemonService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
