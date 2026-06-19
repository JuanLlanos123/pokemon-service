package com.pokemon.service;

import com.pokemon.entity.Entrenador;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EntrenadorService {
    List<Entrenador> findAll();
    Optional<Entrenador> findById(Integer id);
    Optional<Entrenador> findByUuid(UUID uuid);
    Entrenador save(Entrenador entrenador);
    void deleteById(Integer id);
    void capturarPokemon(Integer entrenadorId, Integer pokemonId);
    void liberarPokemon(Integer entrenadorId, Integer pokemonId);
}
