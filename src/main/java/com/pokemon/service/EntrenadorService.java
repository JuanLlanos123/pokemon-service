package com.pokemon.service;

import com.pokemon.entity.Entrenador;
import com.pokemon.entity.Pokemon;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EntrenadorService {
    List<Entrenador> findAll();
    Optional<Entrenador> findById(Integer id);
    Optional<Entrenador> findByUuid(UUID uuid);
    Optional<Entrenador> findByEmail(String email);
    Entrenador save(Entrenador entrenador);
    void deleteById(Integer id);
    void capturarPokemon(Integer entrenadorId, Integer pokemonId);
    void liberarPokemon(Integer entrenadorId, Integer pokemonId);

    /**
     * Captura un pokémon buscando ambas entidades por UUID.
     * Lanza PokemonYaCapturadoException si la relación ya existe.
     * @return lista actualizada de pokémones del entrenador
     */
    List<Pokemon> capturarPokemonPorUuid(UUID uuidEntrenador, UUID uuidPokemon);
}
