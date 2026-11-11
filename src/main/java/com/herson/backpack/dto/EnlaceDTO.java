package com.herson.backpack.dto;

public class EnlaceDTO {
    private Long id;
    private String titulo;
    private String url;

    public EnlaceDTO() {}

    public EnlaceDTO(Long id, String titulo, String url) {
        this.id = id;
        this.titulo = titulo;
        this.url = url;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }
}
