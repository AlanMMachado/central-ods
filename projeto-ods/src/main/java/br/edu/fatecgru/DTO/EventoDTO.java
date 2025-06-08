package br.edu.fatecgru.DTO;

import java.util.Date;
import java.util.Set;

public class EventoDTO {
    private Long id;
    private String titulo;
    private String descricao;
    private String localizacao;
    private UsuarioResumoDTO usuarioHost; // DTO resumido do host
    private Set<UsuarioResumoDTO> inscritos; // Set de DTOs resumidos
    private Set<OdsResumoDTO> ods;
    private Date data;

    // Construtores
    public EventoDTO() {}

    public EventoDTO(Long id, String titulo, String descricao, String localizacao,
                     UsuarioResumoDTO usuarioHost, Set<UsuarioResumoDTO> inscritos, Date data, Set<OdsResumoDTO> ods) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.localizacao = localizacao;
        this.usuarioHost = usuarioHost;
        this.inscritos = inscritos;
        this.data = data;
        this.ods = ods;
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

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

    public UsuarioResumoDTO getUsuarioHost() {
        return usuarioHost;
    }

    public void setUsuarioHost(UsuarioResumoDTO usuarioHost) {
        this.usuarioHost = usuarioHost;
    }

    public Set<UsuarioResumoDTO> getInscritos() {
        return inscritos;
    }

    public void setInscritos(Set<UsuarioResumoDTO> inscritos) {
        this.inscritos = inscritos;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Set<OdsResumoDTO> getOds() {
        return ods;
    }

    public void setOds(Set<OdsResumoDTO> ods) {
        this.ods = ods;
    }
}

