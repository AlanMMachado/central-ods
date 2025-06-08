package br.edu.fatecgru.DTO;

import java.util.Set;
import java.util.stream.Collectors;

public class OdsDTO {
    private Long id;
    private String titulo;
    private Set<EventoResumoDTO> eventos;

    public OdsDTO() {
    }

    public OdsDTO(Long id, String titulo, Set<EventoResumoDTO> eventos) {
        this.id = id;
        this.titulo = titulo;
        this.eventos = eventos;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Set<EventoResumoDTO> getEventos() {
        return eventos;
    }

    public void setEventos(Set<EventoResumoDTO> eventos) {
        this.eventos = eventos;
    }
}