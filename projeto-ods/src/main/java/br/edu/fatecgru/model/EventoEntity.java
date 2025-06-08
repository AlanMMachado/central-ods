package br.edu.fatecgru.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tb_evento")
public class EventoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "titulo_evento", nullable = false, length = 100)
    private String titulo;

    @Column(name = "descricao_evento")
    private String descricao;

    @Column(name = "localizacao_evento")
    private String localizacao;

    @ManyToOne
    @JoinColumn(name = "usuario_host_id", nullable = false)
    private UsuarioEntity usuarioHost;

    @ManyToMany(mappedBy = "eventos")
    @JsonIgnoreProperties({"eventos", "senha"}) // Ignora eventos e senha nos usuários
    private Set<UsuarioEntity> inscritos = new HashSet<>();


    @ManyToMany
    @JoinTable(
            name = "tb_evento_ods",
            joinColumns = @JoinColumn(name = "evento_id"),
            inverseJoinColumns = @JoinColumn(name = "ods_id")
    )
    private Set<OdsEntity> ods = new HashSet<>();


    @Column(name = "data", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date data;

    // Constructors
    public EventoEntity() {
    }

    public EventoEntity(String titulo, String descricao, String localizacao, UsuarioEntity usuarioHost, Set<UsuarioEntity> inscritos, Date data) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.localizacao = localizacao;
        this.usuarioHost = usuarioHost;
        this.inscritos = inscritos;
        this.data = data;
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

    public UsuarioEntity getUsuarioHost() {
        return usuarioHost;
    }

    public void setUsuarioHost(UsuarioEntity usuarioHost) {
        this.usuarioHost = usuarioHost;
    }

    public Set<UsuarioEntity> getInscritos() {
        return inscritos;
    }

    public void addInscrito(UsuarioEntity usuario) {
        this.inscritos.add(usuario);
        usuario.getEventos().add(this); // Garante a sincronização bidirecional
    }

    public void removeInscrito(UsuarioEntity usuario) {
        this.inscritos.remove(usuario);
        usuario.getEventos().remove(this); // Garante a sincronização bidirecional
    }

    public Set<OdsEntity> getOds() {
        return ods;
    }

    public void setOds(Set<OdsEntity> ods) {
        if (this.ods != null) {
            this.ods.forEach(o -> o.getEventos().remove(this));
        }
        this.ods = ods;
        if (ods != null) {
            ods.forEach(o -> o.getEventos().add(this));
        }
    }
    public void setInscritos(Set<UsuarioEntity> inscritos) {
        this.inscritos = inscritos;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public void addOds(OdsEntity ods) {
        this.ods.add(ods);
        ods.getEventos().add(this); // Mantém a consistência bidirecional
    }

    public void removeOds(OdsEntity ods) {
        this.ods.remove(ods);
        ods.getEventos().remove(this); // Mantém a consistência bidirecional
    }


}

