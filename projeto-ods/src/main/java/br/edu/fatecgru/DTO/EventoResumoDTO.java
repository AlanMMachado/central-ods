package br.edu.fatecgru.DTO;

import java.util.Date;

public class EventoResumoDTO {
    private Long id;
    private String titulo;
    private String localizacao;
    private Date data;

    public EventoResumoDTO() {
    }

    public EventoResumoDTO(Long id, String titulo, String localizacao, Date data) {
        this.id = id;
        this.titulo = titulo;
        this.localizacao = localizacao;
        this.data = data;
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

    public String getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }
}