package ufg.IntegraUFG.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ufg.IntegraUFG.model.Usuario;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmailInstitucional(String emailInstitucional);
}
