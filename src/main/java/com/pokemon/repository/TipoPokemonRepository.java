package com.pokemon.repository;

import com.pokemon.entity.TipoPokemon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TipoPokemonRepository extends JpaRepository<TipoPokemon, Integer> {
    Optional<TipoPokemon> findByUuid(UUID uuid);
}
