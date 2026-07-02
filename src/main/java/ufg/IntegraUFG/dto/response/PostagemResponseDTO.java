package ufg.IntegraUFG.dto.response;

import java.time.LocalDateTime;

public record PostagemResponseDTO(
        Long id,
        String nomeAutor,
        String cursoAutor,
        String conteudo,
        LocalDateTime dataCriacao,
        int curtidas
) {
}
