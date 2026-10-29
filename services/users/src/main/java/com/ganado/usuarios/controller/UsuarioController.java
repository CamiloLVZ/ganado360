package com.ganado.usuarios.controller;

import com.ganado.usuarios.dto.*;
import com.ganado.usuarios.mapper.UsuarioMapper;
import com.ganado.usuarios.model.Usuario;
import com.ganado.usuarios.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService service;


    @PostMapping("/register")
    public Usuario register(@RequestBody RegisterDTO dto) {
        return service.register(dto);
    }

    @PostMapping("/validate")
    public Usuario validate(@RequestBody LoginDTO dto) {
        return service.login(dto); // SI password es correcta, retorna user; sino lanza error
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getAll() {
        List<Usuario> users = service.findAll();
        List<UserResponseDTO> dtoList = users.stream()
                .map(UsuarioMapper::toDTO)
                .toList();
        return ResponseEntity.ok(dtoList);
    }

    // GET BY ID
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getById(@PathVariable UUID id) {
        Usuario user = service.findById(id);
        return ResponseEntity.ok(UsuarioMapper.toDTO(user));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UserResponseDTO> getByEmail(@PathVariable String email) {
        Usuario user = service.findByEmail(email);
        return ResponseEntity.ok(UsuarioMapper.toDTO(user));
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> update(
            @PathVariable UUID id,
            @RequestBody UserRequestDTO dto
    ) {
        Usuario updated = service.update(id, dto);
        return ResponseEntity.ok(UsuarioMapper.toDTO(updated));
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.ok(Map.of("message", "Usuario eliminado"));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordDTO dto) {
        service.requestPasswordReset(dto);
        return ResponseEntity.ok(Map.of("message", "Código enviado"));
    }

    @PostMapping("/reset-password/confirm")
    public ResponseEntity<?> confirmReset(@RequestBody ConfirmCodeDTO dto) {
        service.confirmPasswordReset(dto);
        return ResponseEntity.ok(Map.of("message", "Código confirmado"));
    }

    @PostMapping("/reset-password/new")
    public ResponseEntity<?> confirmPassword(@RequestBody PasswordDTO dto){
        service.newPassword(dto);
        return ResponseEntity.ok(Map.of("message", "Contraseña actualizada"));
    }

    @GetMapping("/checkemail")
    public Map<String, Boolean> checkEmail(@RequestParam String email) {
        System.out.println("email: "+email);
        boolean exists = service.emailExists(email);
        System.out.println(exists);
        return Map.of("exists", exists);
    }
}