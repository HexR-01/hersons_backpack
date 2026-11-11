package com.herson.backpack.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "superusuarios")
public class Superusuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "usuario_id", nullable = false, unique = true)
    private Usuario usuario;

    @Column(name = "nivel_acceso", length = 40)
    private String nivelAcceso = "SUPER_ADMIN";

    @Column(name = "fecha_asignacion")
    private LocalDateTime fechaAsignacion = LocalDateTime.now();

    public Superusuario() {}

    public Superusuario(Usuario usuario, String nivelAcceso) {
        this.usuario = usuario;
        this.nivelAcceso = nivelAcceso;
        this.fechaAsignacion = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }

    public String getNivelAcceso() { return nivelAcceso; }
    public void setNivelAcceso(String nivelAcceso) { this.nivelAcceso = nivelAcceso; }

    public LocalDateTime getFechaAsignacion() { return fechaAsignacion; }
    public void setFechaAsignacion(LocalDateTime fechaAsignacion) { this.fechaAsignacion = fechaAsignacion; }
}
