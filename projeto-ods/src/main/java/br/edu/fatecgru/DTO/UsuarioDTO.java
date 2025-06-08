package br.edu.fatecgru.DTO;

import java.util.Set;

public class UsuarioDTO {
    private Long id;
    private String nome;
    private String email;
    private String telefone;
    private String cidade;
    private String senha;
    private Set<EventoResumoDTO> eventos;

    public UsuarioDTO() {
    }

    // Construtor sem a senha (para respostas da API)
    public UsuarioDTO(Long id, String nome, String email, String telefone, String cidade, Set<EventoResumoDTO> eventos) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.cidade = cidade;
        this.eventos = eventos;
    }

    // Construtor completo (para criação/atualização)
    public UsuarioDTO(Long id, String nome, String email, String telefone, String cidade, String senha, Set<EventoResumoDTO> eventos) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.cidade = cidade;
        this.senha = senha;
        this.eventos = eventos;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Set<EventoResumoDTO> getEventos() {
        return eventos;
    }

    public void setEventos(Set<EventoResumoDTO> eventos) {
        this.eventos = eventos;
    }
}