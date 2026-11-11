package com.herson.backpack.controller;

import com.herson.backpack.dto.MensajePrivadoDTO;
import com.herson.backpack.service.MensajePrivadoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/mensajes")
@CrossOrigin(origins = "*")
public class MensajePrivadoController {

    private final MensajePrivadoService mensajeService;

    public MensajePrivadoController(MensajePrivadoService mensajeService) {
        this.mensajeService = mensajeService;
    }

    @GetMapping("/recibidos/{usuarioId}")
    public ResponseEntity<List<MensajePrivadoDTO>> recibidos(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(mensajeService.listarRecibidos(usuarioId));
    }

    @GetMapping("/enviados/{usuarioId}")
    public ResponseEntity<List<MensajePrivadoDTO>> enviados(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(mensajeService.listarEnviados(usuarioId));
    }

    @PostMapping
    public ResponseEntity<?> enviar(@RequestBody Map<String, Object> payload) {
        try {
            Long remitenteId = Long.valueOf(String.valueOf(payload.get("remitenteId")));
            String destinatarioUsername = String.valueOf(payload.get("destinatarioUsername"));
            String asunto = payload.get("asunto") != null ? String.valueOf(payload.get("asunto")) : "";
            String contenido = String.valueOf(payload.get("contenido"));
            Long proyectoInvitadoId = (payload.get("proyectoInvitadoId") != null && !String.valueOf(payload.get("proyectoInvitadoId")).isEmpty())
                    ? Long.valueOf(String.valueOf(payload.get("proyectoInvitadoId")))
                    : null;

            MensajePrivadoDTO enviado = mensajeService.enviarMensaje(remitenteId, destinatarioUsername, asunto, contenido, proyectoInvitadoId);
            return ResponseEntity.status(HttpStatus.CREATED).body(enviado);
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(Map.of("error", ex.getMessage()));
        }
    }

    @PostMapping("/{mensajeId}/responder-invitacion")
    public ResponseEntity<?> responderInvitacion(
            @PathVariable Long mensajeId,
            @RequestBody Map<String, Object> payload) {
        try {
            Long usuarioId = Long.valueOf(String.valueOf(payload.get("usuarioId")));
            boolean aceptar = Boolean.parseBoolean(String.valueOf(payload.get("aceptar")));
            MensajePrivadoDTO actualizado = mensajeService.responderInvitacion(mensajeId, usuarioId, aceptar);
            return ResponseEntity.ok(actualizado);
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(Map.of("error", ex.getMessage()));
        }
    }
}
