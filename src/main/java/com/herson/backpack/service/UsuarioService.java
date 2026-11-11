package com.herson.backpack.service;

import com.herson.backpack.dto.AuthRequestDTO;
import com.herson.backpack.dto.UsuarioDTO;
import com.herson.backpack.model.Usuario;
import com.herson.backpack.repository.SuperusuarioRepository;
import com.herson.backpack.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final SuperusuarioRepository superusuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository, SuperusuarioRepository superusuarioRepository) {
        this.usuarioRepository = usuarioRepository;
        this.superusuarioRepository = superusuarioRepository;
    }

    /**
     * Cifrado SHA-256 (Hexadecimal de 64 caracteres) para no guardar contraseñas en texto plano.
     */
    public String cifrarPassword(String rawPassword) {
        if (rawPassword == null) return "";
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(rawPassword.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder(2 * hashBytes.length);
            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("Error al cifrar la contraseña con SHA-256", e);
        }
    }

    @Transactional(readOnly = true)
    public List<UsuarioDTO> listarUsuarios() {
        return usuarioRepository.findAll().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    /**
     * Autenticación con verificación de hash SHA-256.
     * Si detecta una cuenta antigua en texto plano en la base de datos local, la valida y actualiza su hash automáticamente.
     */
    @Transactional
    public UsuarioDTO autenticar(AuthRequestDTO.LoginRequest request) {
        String uname = request.getUsername() != null ? request.getUsername().trim() : "";
        String rawPass = request.getPassword() != null ? request.getPassword().trim() : "";

        Usuario usuario = usuarioRepository.findByUsernameIgnoreCase(uname)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado: " + uname));

        String hashIngresado = cifrarPassword(rawPass);
        String passGuardada = usuario.getPassword() != null ? usuario.getPassword().trim() : "";

        boolean coincideHash = passGuardada.equalsIgnoreCase(hashIngresado);
        boolean coincideTextoPlanoAntiguo = passGuardada.equals(rawPass);

        if (!coincideHash && !coincideTextoPlanoAntiguo) {
            throw new IllegalArgumentException("Contraseña incorrecta para el usuario: " + uname);
        }

        // Si estaba en texto plano de una versión anterior, la migramos automáticamente a SHA-256 en PostgreSQL
        if (coincideTextoPlanoAntiguo && !coincideHash) {
            usuario.setPassword(hashIngresado);
            usuario = usuarioRepository.save(usuario);
        }

        return convertirADTO(usuario);
    }

    /**
     * Cambio 4: Poder crear nueva cuenta (solo usuarios normales ESTUDIANTE, nunca superusuarios)
     * guardando la contraseña cifrada con SHA-256.
     */
    @Transactional
    public UsuarioDTO registrar(AuthRequestDTO.RegistroRequest request) {
        String uname = request.getUsername().trim();
        String mail = request.getEmail().trim();

        if (usuarioRepository.existsByUsernameIgnoreCase(uname)) {
            throw new IllegalArgumentException("El username '" + uname + "' ya está en uso.");
        }
        if (usuarioRepository.existsByEmailIgnoreCase(mail)) {
            throw new IllegalArgumentException("El correo '" + mail + "' ya está registrado.");
        }

        String passwordCifrada = cifrarPassword(request.getPassword().trim());

        Usuario nuevo = new Usuario(
                uname,
                mail,
                passwordCifrada,
                "ESTUDIANTE"
        );
        Usuario guardado = usuarioRepository.save(nuevo);
        return convertirADTO(guardado);
    }

    public boolean esSuperusuario(Long usuarioId) {
        if (usuarioId == null) return false;
        if (superusuarioRepository.existsByUsuarioId(usuarioId)) return true;
        return usuarioRepository.findById(usuarioId)
                .map(u -> "SUPERUSUARIO".equalsIgnoreCase(u.getRol()) || "ADMIN".equalsIgnoreCase(u.getRol()))
                .orElse(false);
    }

    private UsuarioDTO convertirADTO(Usuario u) {
        boolean isSuper = superusuarioRepository.existsByUsuarioId(u.getId()) ||
                          "SUPERUSUARIO".equalsIgnoreCase(u.getRol()) ||
                          "ADMIN".equalsIgnoreCase(u.getRol());
        return new UsuarioDTO(
                u.getId(),
                u.getUsername(),
                u.getEmail(),
                isSuper ? "SUPERUSUARIO" : u.getRol(),
                isSuper,
                u.getFechaRegistro(),
                u.getProyectos() != null ? u.getProyectos().size() : 0
        );
    }
}
