package com.pokemon.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "pokemon", schema = "pokemon")
public class Pokemon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "tipo_pokemon_id", foreignKey = @ForeignKey(name = "fk_pokemon_tipo"))
    private TipoPokemon tipoPokemon;

    @Column(name = "fecha_descubrimiento", nullable = false)
    private LocalDate fechaDescubrimiento;

    @Column(name = "generacion", nullable = false)
    private Integer generacion;

    @Column(name = "uuid", nullable = false, unique = true)
    private UUID uuid;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "pokemon_captura",
        schema = "pokemon",
        joinColumns = @JoinColumn(name = "pokemon_id", foreignKey = @ForeignKey(name = "fk_captura_pokemon")),
        inverseJoinColumns = @JoinColumn(name = "entrenador_id", foreignKey = @ForeignKey(name = "fk_captura_entrenador"))
    )
    private Set<Entrenador> entrenadores = new HashSet<>();

    public Pokemon() {
    }

    public Pokemon(Integer id, String nombre, String descripcion, TipoPokemon tipoPokemon, LocalDate fechaDescubrimiento, Integer generacion, UUID uuid, Set<Entrenador> entrenadores) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.tipoPokemon = tipoPokemon;
        this.fechaDescubrimiento = fechaDescubrimiento;
        this.generacion = generacion;
        this.uuid = uuid;
        this.entrenadores = entrenadores != null ? entrenadores : new HashSet<>();
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public TipoPokemon getTipoPokemon() {
        return tipoPokemon;
    }

    public void setTipoPokemon(TipoPokemon tipoPokemon) {
        this.tipoPokemon = tipoPokemon;
    }

    public LocalDate getFechaDescubrimiento() {
        return fechaDescubrimiento;
    }

    public void setFechaDescubrimiento(LocalDate fechaDescubrimiento) {
        this.fechaDescubrimiento = fechaDescubrimiento;
    }

    public Integer getGeneracion() {
        return generacion;
    }

    public void setGeneracion(Integer generacion) {
        this.generacion = generacion;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public Set<Entrenador> getEntrenadores() {
        return entrenadores;
    }

    public void setEntrenadores(Set<Entrenador> entrenadores) {
        this.entrenadores = entrenadores;
    }
}
