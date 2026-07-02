package ufg.IntegraUFG.dto.response;

import java.time.LocalDateTime;

public record ComentarioResponseDTO(Long id,
                                    String nomeAutor,
                                    String texto,
                                    LocalDateTime dataComentario,
                                    int curtidas) {
}
