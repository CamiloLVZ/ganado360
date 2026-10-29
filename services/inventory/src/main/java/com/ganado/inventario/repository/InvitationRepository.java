package com.ganado.inventario.repository;

import com.ganado.inventario.model.Invitation;
import com.ganado.inventario.model.InvitationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface InvitationRepository extends JpaRepository<Invitation, UUID> {

    List<Invitation> findByUsuarioId(UUID usuarioId);
    List<Invitation> findByUsuarioIdAndStatus(UUID usuarioId, InvitationStatus status);
    List<Invitation> findByFincaId(UUID fincaId);
}
