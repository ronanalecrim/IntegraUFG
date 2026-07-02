package ufg.IntegraUFG.dto.response;
import java.time.LocalDateTime;

public record EventoResponseDTO(
        Long id,
        String nomeAutor,
        String cursoAutor,
        String titulo,
        String descricao,
        LocalDateTime dataEvento,
        String local,
        int curtidas
) {
}