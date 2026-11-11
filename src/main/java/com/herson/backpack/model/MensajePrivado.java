package com.herson.backpack.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "mensajes_privados")
public class MensajePrivado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "remitente_id", nullable = false)
    private Usuario remitente;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "destinatario_id", nullable = false)
    private Usuario destinatario;

    @Column(length = 150)
    private String asunto;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String contenido;

    @Column(nullable = false)
    private Boolean leido = false;

    @Column(name = "es_invitacion", nullable = false)
    private Boolean esInvitacion = false;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "proyecto_invitado_id")
    private Proyecto proyectoInvitado;

    @Column(name = "estado_invitacion", length = 20)
    private String estadoInvitacion = "NO_APLICA"; // NO_APLICA, PENDIENTE, ACEPTADA, RECHAZADA

    @Column(name = "fecha_envio")
    private LocalDateTime fechaEnvio = LocalDateTime.now();

    public MensajePrivado() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Usuario getRemitente() { return remitente; }
    public void setRemitente(Usuario remitente) { this.remitente = remitente; }

    public Usuario getDestinatario() { return destinatario; }
    public void setDestinatario(Usuario destinatario) { this.destinatario = destinatario; }

    public String getAsunto() { return asunto; }
    public void setAsunto(String asunto) { this.asunto = asunto; }

    public String getContenido() { return contenido; }
    public void setContenido(String contenido) { this.contenido = contenido; }

    public Boolean getLeido() { return leido; }
    public void setLeido(Boolean leido) { this.leido = leido; }

    public Boolean getEsInvitacion() { return esInvitacion; }
    public void setEsInvitacion(Boolean esInvitacion) { this.esInvitacion = esInvitacion; }

    public Proyecto getProyectoInvitado() { return proyectoInvitado; }
    public void setProyectoInvitado(Proyecto proyectoInvitado) { this.proyectoInvitado = proyectoInvitado; }

    public String getEstadoInvitacion() { return estadoInvitacion; }
    public void setEstadoInvitacion(String estadoInvitacion) { this.estadoInvitacion = estadoInvitacion; }

    public LocalDateTime getFechaEnvio() { return fechaEnvio; }
    public void setFechaEnvio(LocalDateTime fechaEnvio) { this.fechaEnvio = fechaEnvio; }
}
