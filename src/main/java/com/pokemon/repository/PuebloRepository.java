package com.pokemon.repository;

import com.pokemon.entity.Pueblo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PuebloRepository extends JpaRepository<Pueblo, Integer> {
    Optional<Pueblo> findByUuid(UUID uuid);
}
