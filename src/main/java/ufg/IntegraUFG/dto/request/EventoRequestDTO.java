package ufg.IntegraUFG.dto.request;

import java.time.LocalDateTime;

public record EventoRequestDTO(Long idAutor, String titulo, String descricao, LocalDateTime dataEvento, String local) {
}
