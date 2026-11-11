package com.herson.backpack.dto;

import java.time.LocalDateTime;

public class ComentarioDTO {
    private Long id;
    private Long proyectoId;
    private Long usuarioId;
    private String autorUsername;
    private String contenido;
    private LocalDateTime fechaCreacion;

    public ComentarioDTO() {}

    public ComentarioDTO(Long id, Long proyectoId, Long usuarioId, String autorUsername, String contenido, LocalDateTime fechaCreacion) {
        this.id = id;
        this.proyectoId = proyectoId;
        this.usuarioId = usuarioId;
        this.autorUsername = autorUsername;
        this.contenido = contenido;
        this.fechaCreacion = fechaCreacion;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getProyectoId() { return proyectoId; }
    public void setProyectoId(Long proyectoId) { this.proyectoId = proyectoId; }

    public Long getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Long usuarioId) { this.usuarioId = usuarioId; }

    public String getAutorUsername() { return autorUsername; }
    public void setAutorUsername(String autorUsername) { this.autorUsername = autorUsername; }

    public String getContenido() { return contenido; }
    public void setContenido(String contenido) { this.contenido = contenido; }

    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }
}
