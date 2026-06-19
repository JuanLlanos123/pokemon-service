package com.pokemon.service;

import com.pokemon.entity.Pokemon;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PokemonService {
    List<Pokemon> findAll();
    List<Pokemon> findByTipo(String tipo);
    List<Pokemon> findByEntrenadorUuid(UUID uuid);
    Optional<Pokemon> findById(Integer id);
    Optional<Pokemon> findByUuid(UUID uuid);
    Pokemon save(Pokemon pokemon);
    void deleteById(Integer id);
}
