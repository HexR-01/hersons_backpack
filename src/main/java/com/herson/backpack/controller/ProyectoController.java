package com.herson.backpack.controller;

import com.herson.backpack.dto.ComentarioDTO;
import com.herson.backpack.dto.ProyectoDTO;
import com.herson.backpack.service.ProyectoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/proyectos")
@CrossOrigin(origins = "*")
public class ProyectoController {

    private final ProyectoService proyectoService;

    public ProyectoController(ProyectoService proyectoService) {
        this.proyectoService = proyectoService;
    }

    @GetMapping
    public ResponseEntity<List<ProyectoDTO>> listarYBuscar(
            @RequestParam(required = false) String q,
            @RequestParam(required = false) String tipo,
            @RequestParam(required = false) Long usuarioId) {
        return ResponseEntity.ok(proyectoService.buscarProyectos(q, tipo, usuarioId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPorId(
            @PathVariable Long id,
            @RequestParam(defaultValue = "true") boolean visitar) {
        try {
            return ResponseEntity.ok(proyectoService.obtenerPorId(id, visitar));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", ex.getMessage()));
        }
    }

    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody ProyectoDTO dto) {
        try {
            ProyectoDTO creado = proyectoService.crearProyecto(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(creado);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(Map.of("error", ex.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(
            @PathVariable Long id,
            @RequestParam(required = false) Long editorUsuarioId,
            @Valid @RequestBody ProyectoDTO dto) {
        try {
            ProyectoDTO actualizado = proyectoService.actualizarProyecto(id, dto, editorUsuarioId);
            return ResponseEntity.ok(actualizado);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(Map.of("error", ex.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(
            @PathVariable Long id,
            @RequestParam(required = false) Long solicitanteUsuarioId) {
        try {
            proyectoService.eliminarProyecto(id, solicitanteUsuarioId);
            return ResponseEntity.ok(Map.of("mensaje", "Proyecto eliminado correctamente", "id", id));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("error", ex.getMessage()));
        }
    }

    @PostMapping("/{id}/comentarios")
    public ResponseEntity<?> comentar(
            @PathVariable Long id,
            @RequestBody Map<String, Object> payload) {
        try {
            Long usuarioId = Long.valueOf(String.valueOf(payload.get("usuarioId")));
            String contenido = String.valueOf(payload.get("contenido"));
            ComentarioDTO nuevo = proyectoService.agregarComentario(id, usuarioId, contenido);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(Map.of("error", ex.getMessage()));
        }
    }
}
