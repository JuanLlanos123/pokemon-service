package com.pokemon.repository;

import com.pokemon.entity.Entrenador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface EntrenadorRepository extends JpaRepository<Entrenador, Integer> {
    Optional<Entrenador> findByUuid(UUID uuid);
    Optional<Entrenador> findByEmail(String email);
}
