package com.herson.backpack.service;

import com.herson.backpack.dto.ComentarioDTO;
import com.herson.backpack.dto.EnlaceDTO;
import com.herson.backpack.dto.ProyectoDTO;
import com.herson.backpack.model.Comentario;
import com.herson.backpack.model.EnlaceProyecto;
import com.herson.backpack.model.Proyecto;
import com.herson.backpack.model.Usuario;
import com.herson.backpack.repository.ComentarioRepository;
import com.herson.backpack.repository.ProyectoRepository;
import com.herson.backpack.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProyectoService {

    private final ProyectoRepository proyectoRepository;
    private final UsuarioRepository usuarioRepository;
    private final ComentarioRepository comentarioRepository;
    private final UsuarioService usuarioService;

    public ProyectoService(ProyectoRepository proyectoRepository,
                           UsuarioRepository usuarioRepository,
                           ComentarioRepository comentarioRepository,
                           UsuarioService usuarioService) {
        this.proyectoRepository = proyectoRepository;
        this.usuarioRepository = usuarioRepository;
        this.comentarioRepository = comentarioRepository;
        this.usuarioService = usuarioService;
    }

    @Transactional(readOnly = true)
    public List<ProyectoDTO> buscarProyectos(String query, String tipo, Long usuarioId) {
        List<Proyecto> lista;

        if (usuarioId != null) {
            // Cambio 2: Si soy dueño o colaborador, el proyecto sale en mi lista
            lista = proyectoRepository.findMisProyectosYColaboraciones(usuarioId);
        } else if (query != null && !query.trim().isEmpty()) {
            lista = proyectoRepository.buscarPorTermino(query.trim());
        } else if (tipo != null && !tipo.trim().isEmpty() && !tipo.equalsIgnoreCase("ALL")) {
            lista = proyectoRepository.findByTipoIgnoreCaseOrderByFechaActualizacionDesc(tipo.trim());
        } else {
            lista = proyectoRepository.findAllByOrderByFechaActualizacionDesc();
        }

        if (tipo != null && !tipo.trim().isEmpty() && !tipo.equalsIgnoreCase("ALL")) {
            String tipoFiltro = tipo.trim().toLowerCase();
            lista = lista.stream()
                    .filter(p -> p.getTipo() != null && p.getTipo().toLowerCase().contains(tipoFiltro))
                    .collect(Collectors.toList());
        }

        return lista.stream().map(this::convertirADTO).collect(Collectors.toList());
    }

    /**
     * Cambio 1: Marcador de visitas. Cada vez que alguien consulta el detalle del proyecto,
     * se incrementa el contador de visitas (si incrementarVisita = true).
     */
    @Transactional
    public ProyectoDTO obtenerPorId(Long id, boolean incrementarVisita) {
        Proyecto p = proyectoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No existe el proyecto con ID: " + id));
        if (incrementarVisita) {
            p.incrementarVisitas();
            p = proyectoRepository.save(p);
        }
        return convertirADTO(p);
    }

    @Transactional
    public ProyectoDTO crearProyecto(ProyectoDTO dto) {
        Usuario autor = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new IllegalArgumentException("Usuario autor no encontrado con ID: " + dto.getUsuarioId()));

        String descLarga = dto.getDescripcion() != null ? dto.getDescripcion().trim() : "";
        String descCorta = (dto.getDescripcionCorta() != null && !dto.getDescripcionCorta().trim().isEmpty())
                ? dto.getDescripcionCorta().trim()
                : (descLarga.length() > 160 ? descLarga.substring(0, 157) + "..." : descLarga);

        Proyecto nuevo = new Proyecto(
                dto.getTitulo().trim(),
                dto.getTipo().trim(),
                descCorta,
                descLarga,
                dto.getRepositorioUrl() != null ? dto.getRepositorioUrl().trim() : "",
                dto.getTecnologias() != null ? dto.getTecnologias().trim() : "",
                autor
        );

        // Tabla 2 y Cambio 3: Guardar múltiples enlaces
        if (dto.getEnlaces() != null && !dto.getEnlaces().isEmpty()) {
            for (EnlaceDTO e : dto.getEnlaces()) {
                if (e.getUrl() != null && !e.getUrl().trim().isEmpty()) {
                    String t = (e.getTitulo() != null && !e.getTitulo().trim().isEmpty()) ? e.getTitulo().trim() : "Repositorio";
                    nuevo.agregarEnlace(t, e.getUrl().trim());
                }
            }
            if (nuevo.getRepositorioUrl().isEmpty() && !nuevo.getEnlaces().isEmpty()) {
                nuevo.setRepositorioUrl(nuevo.getEnlaces().get(0).getUrl());
            }
        } else if (nuevo.getRepositorioUrl() != null && !nuevo.getRepositorioUrl().isEmpty()) {
            nuevo.agregarEnlace("Repositorio Principal", nuevo.getRepositorioUrl());
        }

        Proyecto guardado = proyectoRepository.save(nuevo);
        return convertirADTO(guardado);
    }

    /**
     * Permisos de edición:
     * - Dueño del proyecto: Puede modificar
     * - Colaborador del proyecto: Puede modificar
     * - Superusuario: Puede modificar cualquier publicación
     */
    @Transactional
    public ProyectoDTO actualizarProyecto(Long id, ProyectoDTO dto, Long editorUsuarioId) {
        Proyecto existente = proyectoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró el proyecto con ID: " + id));

        Long actorId = editorUsuarioId != null ? editorUsuarioId : dto.getUsuarioId();
        if (!puedeModificar(existente, actorId)) {
            throw new IllegalArgumentException("No tienes permisos para modificar este proyecto.");
        }

        String descLarga = dto.getDescripcion() != null ? dto.getDescripcion().trim() : existente.getDescripcion();
        String descCorta = (dto.getDescripcionCorta() != null && !dto.getDescripcionCorta().trim().isEmpty())
                ? dto.getDescripcionCorta().trim()
                : (descLarga.length() > 160 ? descLarga.substring(0, 157) + "..." : descLarga);

        existente.setTitulo(dto.getTitulo().trim());
        existente.setTipo(dto.getTipo().trim());
        existente.setDescripcionCorta(descCorta);
        existente.setDescripcion(descLarga);
        existente.setRepositorioUrl(dto.getRepositorioUrl() != null ? dto.getRepositorioUrl().trim() : "");
        existente.setTecnologias(dto.getTecnologias() != null ? dto.getTecnologias().trim() : "");
        existente.setFechaActualizacion(LocalDateTime.now());

        // Actualizar múltiples enlaces
        if (dto.getEnlaces() != null) {
            existente.getEnlaces().clear();
            for (EnlaceDTO e : dto.getEnlaces()) {
                if (e.getUrl() != null && !e.getUrl().trim().isEmpty()) {
                    String t = (e.getTitulo() != null && !e.getTitulo().trim().isEmpty()) ? e.getTitulo().trim() : "Repositorio";
                    existente.agregarEnlace(t, e.getUrl().trim());
                }
            }
            if (!existente.getEnlaces().isEmpty()) {
                existente.setRepositorioUrl(existente.getEnlaces().get(0).getUrl());
            }
        }

        Proyecto actualizado = proyectoRepository.save(existente);
        return convertirADTO(actualizado);
    }

    /**
     * Permisos de eliminación:
     * - Dueño del proyecto: Puede eliminar
     * - Superusuario: Puede eliminar cualquier publicación
     * - Colaborador: NO puede eliminar (solo modificar)
     */
    @Transactional
    public void eliminarProyecto(Long id, Long solicitanteUsuarioId) {
        Proyecto existente = proyectoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No existe el proyecto con ID: " + id));

        if (solicitanteUsuarioId != null && !puedeEliminar(existente, solicitanteUsuarioId)) {
            throw new IllegalArgumentException("Los colaboradores no pueden eliminar el proyecto; solo el dueño o un superusuario.");
        }
        proyectoRepository.delete(existente);
    }

    @Transactional
    public ComentarioDTO agregarComentario(Long proyectoId, Long usuarioId, String contenido) {
        Proyecto proyecto = proyectoRepository.findById(proyectoId)
                .orElseThrow(() -> new IllegalArgumentException("Proyecto no encontrado"));
        Usuario autor = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        Comentario c = new Comentario(contenido.trim(), proyecto, autor);
        Comentario guardado = comentarioRepository.save(c);
        return new ComentarioDTO(
                guardado.getId(),
                proyecto.getId(),
                autor.getId(),
                autor.getUsername(),
                guardado.getContenido(),
                guardado.getFechaCreacion()
        );
    }

    public boolean puedeModificar(Proyecto p, Long usuarioId) {
        if (usuarioId == null) return false;
        if (usuarioService.esSuperusuario(usuarioId)) return true;
        if (p.getUsuario().getId().equals(usuarioId)) return true;
        return p.getColaboradores().stream().anyMatch(u -> u.getId().equals(usuarioId));
    }

    public boolean puedeEliminar(Proyecto p, Long usuarioId) {
        if (usuarioId == null) return false;
        if (usuarioService.esSuperusuario(usuarioId)) return true;
        return p.getUsuario().getId().equals(usuarioId);
    }

    public ProyectoDTO convertirADTO(Proyecto p) {
        ProyectoDTO dto = new ProyectoDTO();
        dto.setId(p.getId());
        dto.setTitulo(p.getTitulo());
        dto.setTipo(p.getTipo());
        dto.setDescripcionCorta(p.getDescripcionCorta() != null ? p.getDescripcionCorta() : p.getDescripcion());
        dto.setDescripcion(p.getDescripcion());
        dto.setRepositorioUrl(p.getRepositorioUrl());
        dto.setTecnologias(p.getTecnologias());
        dto.setVisitas(p.getVisitas() != null ? p.getVisitas() : 0L);
        dto.setFechaCreacion(p.getFechaCreacion());
        dto.setFechaActualizacion(p.getFechaActualizacion());
        dto.setUsuarioId(p.getUsuario().getId());
        dto.setAutorUsername(p.getUsuario().getUsername());

        List<EnlaceDTO> enlacesDto = new ArrayList<>();
        if (p.getEnlaces() != null && !p.getEnlaces().isEmpty()) {
            for (EnlaceProyecto e : p.getEnlaces()) {
                enlacesDto.add(new EnlaceDTO(e.getId(), e.getTitulo(), e.getUrl()));
            }
        } else if (p.getRepositorioUrl() != null && !p.getRepositorioUrl().isEmpty()) {
            enlacesDto.add(new EnlaceDTO(null, "Repositorio Principal", p.getRepositorioUrl()));
        }
        dto.setEnlaces(enlacesDto);

        List<Comentario> comentariosList = comentarioRepository.findByProyectoIdOrderByFechaCreacionDesc(p.getId());
        List<ComentarioDTO> comentariosDto = comentariosList.stream()
                .map(c -> new ComentarioDTO(
                        c.getId(),
                        p.getId(),
                        c.getUsuario().getId(),
                        c.getUsuario().getUsername(),
                        c.getContenido(),
                        c.getFechaCreacion()
                )).collect(Collectors.toList());
        dto.setComentarios(comentariosDto);

        if (p.getColaboradores() != null) {
            dto.setColaboradoresIds(p.getColaboradores().stream().map(Usuario::getId).collect(Collectors.toList()));
            dto.setColaboradoresUsernames(p.getColaboradores().stream().map(Usuario::getUsername).collect(Collectors.toList()));
        }

        return dto;
    }
}
