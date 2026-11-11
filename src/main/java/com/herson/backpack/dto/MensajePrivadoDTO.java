package com.herson.backpack.dto;

import java.time.LocalDateTime;

public class MensajePrivadoDTO {
    private Long id;
    private Long remitenteId;
    private String remitenteUsername;
    private Long destinatarioId;
    private String destinatarioUsername;
    private String asunto;
    private String contenido;
    private Boolean leido;
    private Boolean esInvitacion;
    private Long proyectoInvitadoId;
    private String proyectoInvitadoTitulo;
    private String estadoInvitacion;
    private LocalDateTime fechaEnvio;

    public MensajePrivadoDTO() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getRemitenteId() { return remitenteId; }
    public void setRemitenteId(Long remitenteId) { this.remitenteId = remitenteId; }

    public String getRemitenteUsername() { return remitenteUsername; }
    public void setRemitenteUsername(String remitenteUsername) { this.remitenteUsername = remitenteUsername; }

    public Long getDestinatarioId() { return destinatarioId; }
    public void setDestinatarioId(Long destinatarioId) { this.destinatarioId = destinatarioId; }

    public String getDestinatarioUsername() { return destinatarioUsername; }
    public void setDestinatarioUsername(String destinatarioUsername) { this.destinatarioUsername = destinatarioUsername; }

    public String getAsunto() { return asunto; }
    public void setAsunto(String asunto) { this.asunto = asunto; }

    public String getContenido() { return contenido; }
    public void setContenido(String contenido) { this.contenido = contenido; }

    public Boolean getLeido() { return leido; }
    public void setLeido(Boolean leido) { this.leido = leido; }

    public Boolean getEsInvitacion() { return esInvitacion; }
    public void setEsInvitacion(Boolean esInvitacion) { this.esInvitacion = esInvitacion; }

    public Long getProyectoInvitadoId() { return proyectoInvitadoId; }
    public void setProyectoInvitadoId(Long proyectoInvitadoId) { this.proyectoInvitadoId = proyectoInvitadoId; }

    public String getProyectoInvitadoTitulo() { return proyectoInvitadoTitulo; }
    public void setProyectoInvitadoTitulo(String proyectoInvitadoTitulo) { this.proyectoInvitadoTitulo = proyectoInvitadoTitulo; }

    public String getEstadoInvitacion() { return estadoInvitacion; }
    public void setEstadoInvitacion(String estadoInvitacion) { this.estadoInvitacion = estadoInvitacion; }

    public LocalDateTime getFechaEnvio() { return fechaEnvio; }
    public void setFechaEnvio(LocalDateTime fechaEnvio) { this.fechaEnvio = fechaEnvio; }
}
