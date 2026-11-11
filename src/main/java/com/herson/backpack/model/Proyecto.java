package com.herson.backpack.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "proyectos")
public class Proyecto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 120)
    private String titulo;

    @Column(nullable = false, length = 60)
    private String tipo;

    @Column(name = "descripcion_corta", length = 280)
    private String descripcionCorta;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String descripcion; // Descripción larga detallada

    @Column(name = "repositorio_url", length = 255)
    private String repositorioUrl;

    @Column(length = 180)
    private String tecnologias;

    @Column(nullable = false)
    private Long visitas = 0L;

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion = LocalDateTime.now();

    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion = LocalDateTime.now();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario; // Dueño del proyecto

    @OneToMany(mappedBy = "proyecto", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<EnlaceProyecto> enlaces = new ArrayList<>();

    @OneToMany(mappedBy = "proyecto", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Comentario> comentarios = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "proyecto_colaboradores",
        joinColumns = @JoinColumn(name = "proyecto_id"),
        inverseJoinColumns = @JoinColumn(name = "usuario_id")
    )
    private Set<Usuario> colaboradores = new HashSet<>();

    public Proyecto() {
    }

    public Proyecto(String titulo, String tipo, String descripcionCorta, String descripcion, String repositorioUrl, String tecnologias, Usuario usuario) {
        this.titulo = titulo;
        this.tipo = tipo;
        this.descripcionCorta = descripcionCorta;
        this.descripcion = descripcion;
        this.repositorioUrl = repositorioUrl;
        this.tecnologias = tecnologias;
        this.usuario = usuario;
        this.visitas = 0L;
        this.fechaCreacion = LocalDateTime.now();
        this.fechaActualizacion = LocalDateTime.now();
    }

    public void incrementarVisitas() {
        this.visitas = (this.visitas == null ? 0L : this.visitas) + 1L;
    }

    public void agregarEnlace(String tituloEnlace, String urlEnlace) {
        EnlaceProyecto enlace = new EnlaceProyecto(tituloEnlace, urlEnlace, this);
        this.enlaces.add(enlace);
    }

    @PreUpdate
    public void preUpdate() {
        this.fechaActualizacion = LocalDateTime.now();
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

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }

    public List<EnlaceProyecto> getEnlaces() { return enlaces; }
    public void setEnlaces(List<EnlaceProyecto> enlaces) { this.enlaces = enlaces; }

    public List<Comentario> getComentarios() { return comentarios; }
    public void setComentarios(List<Comentario> comentarios) { this.comentarios = comentarios; }

    public Set<Usuario> getColaboradores() { return colaboradores; }
    public void setColaboradores(Set<Usuario> colaboradores) { this.colaboradores = colaboradores; }
}
