package com.pokemon.entity;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "pueblo", schema = "pokemon")
public class Pueblo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "uuid", nullable = false, unique = true)
    private UUID uuid;

    public Pueblo() {
    }

    public Pueblo(Integer id, String nombre, UUID uuid) {
        this.id = id;
        this.nombre = nombre;
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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }
}
