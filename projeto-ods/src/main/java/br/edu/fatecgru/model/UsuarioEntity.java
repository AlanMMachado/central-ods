package br.edu.fatecgru.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tb_usuario")
public class UsuarioEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "telefone", nullable = false)
    private String telefone;

    @Column(name = "cidade", nullable = false)
    private String cidade;

    @Column(name = "senha", nullable = false)
    private String senha;

    //CRIA UMA TABELA DE RELACIONAMENTO ENTRE EVENTO E USUÁRIO
    //GARANTE QUE UM USUÁRIO POSSA TER MAIS DE UM EVENTO RELACIONADO
    //GARANTE QUE UM EVENTO POSSA TER MAIS DE UM USUÁRIO RELACIONADO


    @ManyToMany
    @JoinTable(
            name = "tb_usuario_evento",
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "evento_id")
    )
    @JsonIgnore // Ignora completamente a lista de eventos
    private Set<EventoEntity> eventos = new HashSet<>();

    // Constructors
    public UsuarioEntity() {
    }

    public UsuarioEntity(String nome, String email, String telefone, String cidade, String senha) {
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.cidade = cidade;
        this.senha = senha;
    }

    // Helper methods for relationship management
    public void addEvento(EventoEntity evento) {
        this.eventos.add(evento);
        evento.getInscritos().add(this);
    }

    public void removeEvento(EventoEntity evento) {
        this.eventos.remove(evento);
        evento.getInscritos().remove(this);
    }


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

    public Set<EventoEntity> getEventos() {
        return eventos;
    }

    public void setEventos(Set<EventoEntity> eventos) {
        this.eventos = eventos;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
