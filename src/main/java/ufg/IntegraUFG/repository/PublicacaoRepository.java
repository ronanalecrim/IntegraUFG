package ufg.IntegraUFG.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ufg.IntegraUFG.model.EventoAcademico;
import ufg.IntegraUFG.model.Publicacao;

public interface PublicacaoRepository extends JpaRepository<Publicacao, Long> {
}
