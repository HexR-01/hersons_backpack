package com.herson.backpack.controller;

import com.herson.backpack.dto.AuthRequestDTO;
import com.herson.backpack.dto.UsuarioDTO;
import com.herson.backpack.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "*")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> listarTodos() {
        return ResponseEntity.ok(usuarioService.listarUsuarios());
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody AuthRequestDTO.LoginRequest request) {
        try {
            UsuarioDTO usuario = usuarioService.autenticar(request);
            return ResponseEntity.ok(usuario);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", ex.getMessage()));
        }
    }

    @PostMapping("/registro")
    public ResponseEntity<?> registrar(@Valid @RequestBody AuthRequestDTO.RegistroRequest request) {
        try {
            UsuarioDTO nuevo = usuarioService.registrar(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", ex.getMessage()));
        }
    }
}
