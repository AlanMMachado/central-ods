package br.edu.fatecgru.DTO;

// Novo DTO para resumo do usuário
public class UsuarioResumoDTO {
    private Long id;
    private String nome;
    private String email;

    public UsuarioResumoDTO() {
    }

    public UsuarioResumoDTO(Long id, String nome, String email) {
        this.id = id;
        this.nome = nome;
        this.email = email;
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
}
