package com.herson.backpack.dto;

import java.time.LocalDateTime;

public class UsuarioDTO {

    private Long id;
    private String username;
    private String email;
    private String rol;
    private boolean esSuperusuario;
    private LocalDateTime fechaRegistro;
    private int totalProyectos;

    public UsuarioDTO() {
    }

    public UsuarioDTO(Long id, String username, String email, String rol, boolean esSuperusuario, LocalDateTime fechaRegistro, int totalProyectos) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.rol = rol;
        this.esSuperusuario = esSuperusuario;
        this.fechaRegistro = fechaRegistro;
        this.totalProyectos = totalProyectos;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }

    public boolean isEsSuperusuario() { return esSuperusuario; }
    public void setEsSuperusuario(boolean esSuperusuario) { this.esSuperusuario = esSuperusuario; }

    public LocalDateTime getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(LocalDateTime fechaRegistro) { this.fechaRegistro = fechaRegistro; }

    public int getTotalProyectos() { return totalProyectos; }
    public void setTotalProyectos(int totalProyectos) { this.totalProyectos = totalProyectos; }
}
