package br.edu.fatecgru.model;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tb_ods")
public class OdsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String titulo;

    @ManyToMany(mappedBy = "ods")
    private Set<EventoEntity> eventos = new HashSet<>();


    // Constructors, getters e setters
    public OdsEntity() {}

    public OdsEntity(String titulo) {
        this.titulo = titulo;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public Set<EventoEntity> getEventos() {
        return eventos;
    }

    public void setEventos(Set<EventoEntity> eventos) {
        this.eventos = eventos;
    }
}


