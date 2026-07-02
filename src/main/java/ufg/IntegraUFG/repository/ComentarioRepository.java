package ufg.IntegraUFG.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ufg.IntegraUFG.model.Comentario;

public interface ComentarioRepository extends JpaRepository<Comentario, Long> {
}
