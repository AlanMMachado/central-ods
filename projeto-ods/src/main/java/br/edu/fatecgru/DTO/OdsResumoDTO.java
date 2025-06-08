package br.edu.fatecgru.DTO;

public class OdsResumoDTO {
    private Long id;
    private String titulo;

    public OdsResumoDTO() {
    }

    public OdsResumoDTO(Long id, String titulo) {
        this.id = id;
        this.titulo = titulo;
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
}