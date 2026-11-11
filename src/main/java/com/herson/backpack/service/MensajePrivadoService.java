package com.herson.backpack.service;

import com.herson.backpack.dto.MensajePrivadoDTO;
import com.herson.backpack.model.MensajePrivado;
import com.herson.backpack.model.Proyecto;
import com.herson.backpack.model.Usuario;
import com.herson.backpack.repository.MensajePrivadoRepository;
import com.herson.backpack.repository.ProyectoRepository;
import com.herson.backpack.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MensajePrivadoService {

    private final MensajePrivadoRepository mensajeRepository;
    private final UsuarioRepository usuarioRepository;
    private final ProyectoRepository proyectoRepository;
    private final UsuarioService usuarioService;

    public MensajePrivadoService(MensajePrivadoRepository mensajeRepository,
                                 UsuarioRepository usuarioRepository,
                                 ProyectoRepository proyectoRepository,
                                 UsuarioService usuarioService) {
        this.mensajeRepository = mensajeRepository;
        this.usuarioRepository = usuarioRepository;
        this.proyectoRepository = proyectoRepository;
        this.usuarioService = usuarioService;
    }

    @Transactional(readOnly = true)
    public List<MensajePrivadoDTO> listarRecibidos(Long usuarioId) {
        return mensajeRepository.findByDestinatarioIdOrderByFechaEnvioDesc(usuarioId)
                .stream().map(this::convertirADTO).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<MensajePrivadoDTO> listarEnviados(Long usuarioId) {
        return mensajeRepository.findByRemitenteIdOrderByFechaEnvioDesc(usuarioId)
                .stream().map(this::convertirADTO).collect(Collectors.toList());
    }

    @Transactional
    public MensajePrivadoDTO enviarMensaje(Long remitenteId, String destinatarioUsername, String asunto, String contenido, Long proyectoInvitadoId) {
        Usuario remitente = usuarioRepository.findById(remitenteId)
                .orElseThrow(() -> new IllegalArgumentException("Remitente no encontrado"));
        Usuario destinatario = usuarioRepository.findByUsernameIgnoreCase(destinatarioUsername.trim())
                .orElseThrow(() -> new IllegalArgumentException("El usuario '" + destinatarioUsername + "' no existe."));

        MensajePrivado msg = new MensajePrivado();
        msg.setRemitente(remitente);
        msg.setDestinatario(destinatario);
        msg.setAsunto(asunto != null && !asunto.trim().isEmpty() ? asunto.trim() : "Mensaje directo");
        msg.setContenido(contenido.trim());

        if (proyectoInvitadoId != null) {
            Proyecto p = proyectoRepository.findById(proyectoInvitadoId)
                    .orElseThrow(() -> new IllegalArgumentException("El proyecto a invitar no existe"));
            if (!p.getUsuario().getId().equals(remitenteId) && !usuarioService.esSuperusuario(remitenteId)) {
                throw new IllegalArgumentException("Solo el dueño del proyecto puede invitar colaboradores.");
            }
            msg.setEsInvitacion(true);
            msg.setProyectoInvitado(p);
            msg.setEstadoInvitacion("PENDIENTE");
            if (asunto == null || asunto.trim().isEmpty()) {
                msg.setAsunto("Invitación a colaborar en: " + p.getTitulo());
            }
        }

        MensajePrivado guardado = mensajeRepository.save(msg);
        return convertirADTO(guardado);
    }

    @Transactional
    public MensajePrivadoDTO responderInvitacion(Long mensajeId, Long usuarioId, boolean aceptar) {
        MensajePrivado msg = mensajeRepository.findById(mensajeId)
                .orElseThrow(() -> new IllegalArgumentException("Mensaje no encontrado"));

        if (!msg.getDestinatario().getId().equals(usuarioId)) {
            throw new IllegalArgumentException("Solo el destinatario puede responder esta invitación.");
        }
        if (!Boolean.TRUE.equals(msg.getEsInvitacion()) || msg.getProyectoInvitado() == null) {
            throw new IllegalArgumentException("Este mensaje no es una invitación a proyecto.");
        }

        if (aceptar) {
            msg.setEstadoInvitacion("ACEPTADA");
            Proyecto p = msg.getProyectoInvitado();
            p.getColaboradores().add(msg.getDestinatario());
            proyectoRepository.save(p);
        } else {
            msg.setEstadoInvitacion("RECHAZADA");
        }
        msg.setLeido(true);
        return convertirADTO(mensajeRepository.save(msg));
    }

    private MensajePrivadoDTO convertirADTO(MensajePrivado m) {
        MensajePrivadoDTO dto = new MensajePrivadoDTO();
        dto.setId(m.getId());
        dto.setRemitenteId(m.getRemitente().getId());
        dto.setRemitenteUsername(m.getRemitente().getUsername());
        dto.setDestinatarioId(m.getDestinatario().getId());
        dto.setDestinatarioUsername(m.getDestinatario().getUsername());
        dto.setAsunto(m.getAsunto());
        dto.setContenido(m.getContenido());
        dto.setLeido(m.getLeido());
        dto.setEsInvitacion(m.getEsInvitacion());
        if (m.getProyectoInvitado() != null) {
            dto.setProyectoInvitadoId(m.getProyectoInvitado().getId());
            dto.setProyectoInvitadoTitulo(m.getProyectoInvitado().getTitulo());
        }
        dto.setEstadoInvitacion(m.getEstadoInvitacion());
        dto.setFechaEnvio(m.getFechaEnvio());
        return dto;
    }
}
