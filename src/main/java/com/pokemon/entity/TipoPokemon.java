package com.pokemon.entity;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "tipo_pokemon", schema = "pokemon")
public class TipoPokemon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "descripcion", nullable = false, length = 100)
    private String descripcion;

    @Column(name = "uuid", nullable = false, unique = true)
    private UUID uuid;

    public TipoPokemon() {
    }

    public TipoPokemon(Integer id, String descripcion, UUID uuid) {
        this.id = id;
        this.descripcion = descripcion;
        this.uuid = uuid;
    }

    @PrePersist
    protected void onCreate() {
        if (this.uuid == null) {
            this.uuid = UUID.randomUUID();
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }
}
