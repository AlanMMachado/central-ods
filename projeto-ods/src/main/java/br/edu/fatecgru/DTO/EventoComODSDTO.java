package br.edu.fatecgru.DTO;

import java.util.Date;
import java.util.Set;

public class EventoComODSDTO {
    private String titulo;
    private String descricao;
    private String localizacao;
    private Date data;
    private Set<Long> odsIds; // IDs das ODS (1-17)

    // Getters e Setters


    public EventoComODSDTO(String titulo, String descricao, String localizacao, Date data, Set<Long> odsIds) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.localizacao = localizacao;
        this.data = data;
        this.odsIds = odsIds;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
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

    public Set<Long> getOdsIds() {
        return odsIds;
    }

    public void setOdsIds(Set<Long> odsIds) {
        this.odsIds = odsIds;
    }
}