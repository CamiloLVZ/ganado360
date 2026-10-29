package com.ganado.usuarios.service;

import com.ganado.usuarios.dto.*;
import com.ganado.usuarios.model.Usuario;
import com.ganado.usuarios.repository.UsuarioRepository;
import io.jsonwebtoken.security.Password;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository repo;
    private final JavaMailSender mailSender;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    // ✔ REGISTER (usa RegisterDTO)
    public Usuario register(RegisterDTO dto) {
        Usuario user = Usuario.builder()
                .nombre(dto.getName())
                .email(dto.getEmail())
                .password(encoder.encode(dto.getPassword()))
                .fechaCreacion(LocalDateTime.now())
                .build();

        return repo.save(user);
    }

    // ✔ LOGIN (validación)
    public Usuario login(LoginDTO dto) {
        Usuario user = repo.findByEmail(dto.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (!encoder.matches(dto.getPassword(), user.getPassword())) {
            throw new RuntimeException("Contraseña incorrecta");
        }

        return user;
    }

    // ✔ GET ALL
    public List<Usuario> findAll() {
        return repo.findAll();
    }

    // ✔ GET BY ID
    public Usuario findById(UUID id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

    public Usuario findByEmail(String email) {
        return repo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

    // ✔ UPDATE (usa UserRequestDTO)
    public Usuario update(UUID id, UserRequestDTO dto) {
        Usuario user = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (dto.getNombre() != null) {
            user.setNombre(dto.getNombre());
        }

        if (dto.getEmail() != null) {
            user.setEmail(dto.getEmail());
        }

        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            user.setPassword(encoder.encode(dto.getPassword()));
        }

        return repo.save(user);
    }

    // ✔ DELETE
    public void delete(UUID id) {
        if (!repo.existsById(id)) {
            throw new RuntimeException("Usuario no encontrado");
        }
        repo.deleteById(id);
    }

    // ✔ REQUEST PASSWORD RESET
    public void requestPasswordReset(ResetPasswordDTO dto) {
        Usuario user = repo.findByEmail(dto.getEmail())
                .orElseThrow(() -> new RuntimeException("No existe ese correo"));

        String code = UUID.randomUUID().toString().substring(0, 6);
        user.setResetCode(code);
        repo.save(user);

        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(user.getEmail());
        msg.setSubject("Código para cambio de contraseña");
        msg.setText("Tu código es: " + code);

        mailSender.send(msg);
    }

    // ✔ CONFIRM RESET CODE
    public void confirmPasswordReset(ConfirmCodeDTO dto) {
        repo.findByResetCode(dto.getCode())
                .orElseThrow(() -> new RuntimeException("Código inválido"));
    }

    // ✔ UPDATE PASSWORD
    public void newPassword(PasswordDTO dto) {
        Usuario user = repo.findByEmail(dto.getEmail())
                .orElseThrow(() -> new RuntimeException("Error al actualizar"));

        user.setPassword(encoder.encode(dto.getPassword()));
        user.setResetCode(null);
        repo.save(user);
    }

    // ✔ EMAIL CHECK
    public boolean emailExists(String email) {
        return repo.existsByEmail(email);
    }
}