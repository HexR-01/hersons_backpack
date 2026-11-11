package com.herson.backpack.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ProyectoDTO {

    private Long id;

    @NotBlank(message = "El título del proyecto es obligatorio")
    private String titulo;

    @NotBlank(message = "El tipo/categoría del proyecto es obligatorio")
    private String tipo;

    private String descripcionCorta;

    @NotBlank(message = "La descripción larga del proyecto es obligatoria")
    private String descripcion;

    private String repositorioUrl;
    private String tecnologias;
    private Long visitas = 0L;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;

    @NotNull(message = "Debe especificar el ID del usuario")
    private Long usuarioId;

    private String autorUsername;

    private List<EnlaceDTO> enlaces = new ArrayList<>();
    private List<ComentarioDTO> comentarios = new ArrayList<>();
    private List<Long> colaboradoresIds = new ArrayList<>();
    private List<String> colaboradoresUsernames = new ArrayList<>();

    public ProyectoDTO() {
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getDescripcionCorta() { return descripcionCorta; }
    public void setDescripcionCorta(String descripcionCorta) { this.descripcionCorta = descripcionCorta; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getRepositorioUrl() { return repositorioUrl; }
    public void setRepositorioUrl(String repositorioUrl) { this.repositorioUrl = repositorioUrl; }

    public String getTecnologias() { return tecnologias; }
    public void setTecnologias(String tecnologias) { this.tecnologias = tecnologias; }

    public Long getVisitas() { return visitas; }
    public void setVisitas(Long visitas) { this.visitas = visitas; }

    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }

    public LocalDateTime getFechaActualizacion() { return fechaActualizacion; }
    public void setFechaActualizacion(LocalDateTime fechaActualizacion) { this.fechaActualizacion = fechaActualizacion; }

    public Long getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Long usuarioId) { this.usuarioId = usuarioId; }

    public String getAutorUsername() { return autorUsername; }
    public void setAutorUsername(String autorUsername) { this.autorUsername = autorUsername; }

    public List<EnlaceDTO> getEnlaces() { return enlaces; }
    public void setEnlaces(List<EnlaceDTO> enlaces) { this.enlaces = enlaces; }

    public List<ComentarioDTO> getComentarios() { return comentarios; }
    public void setComentarios(List<ComentarioDTO> comentarios) { this.comentarios = comentarios; }

    public List<Long> getColaboradoresIds() { return colaboradoresIds; }
    public void setColaboradoresIds(List<Long> colaboradoresIds) { this.colaboradoresIds = colaboradoresIds; }

    public List<String> getColaboradoresUsernames() { return colaboradoresUsernames; }
    public void setColaboradoresUsernames(List<String> colaboradoresUsernames) { this.colaboradoresUsernames = colaboradoresUsernames; }
}
