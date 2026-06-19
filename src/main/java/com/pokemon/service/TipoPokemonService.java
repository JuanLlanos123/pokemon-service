package com.pokemon.service;

import com.pokemon.entity.TipoPokemon;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TipoPokemonService {
    List<TipoPokemon> findAll();
    Optional<TipoPokemon> findById(Integer id);
    Optional<TipoPokemon> findByUuid(UUID uuid);
    TipoPokemon save(TipoPokemon tipoPokemon);
    void deleteById(Integer id);
}
