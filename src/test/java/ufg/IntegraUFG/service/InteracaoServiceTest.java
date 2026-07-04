package ufg.IntegraUFG.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ufg.IntegraUFG.dto.request.ComentarioRequestDTO;
import ufg.IntegraUFG.dto.response.ComentarioResponseDTO;
import ufg.IntegraUFG.model.Comentario;
import ufg.IntegraUFG.model.PostagemTexto;
import ufg.IntegraUFG.model.Usuario;
import ufg.IntegraUFG.repository.ComentarioRepository;
import ufg.IntegraUFG.repository.PublicacaoRepository;
import ufg.IntegraUFG.repository.UsuarioRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("InteracaoService - Testes Unitários")
class InteracaoServiceTest {

    @Mock
    private PublicacaoRepository publicacaoRepo;

    @Mock
    private ComentarioRepository comentarioRepo;

    @Mock
    private UsuarioRepository usuarioRepo;

    @InjectMocks
    private InteracaoService interacaoService;

    private Usuario autor;
    private PostagemTexto publicacao;

    @BeforeEach
    void setUp() {
        autor = new Usuario(1L, "Ana Paula", "ana@ufg.br", "hashed", "Matemática");
        publicacao = new PostagemTexto(1L, autor, "Conteúdo da publicação");
    }

    @Test
    @DisplayName("Deve adicionar comentário com sucesso")
    void deveAdicionarComentario() {
        ComentarioRequestDTO dto = new ComentarioRequestDTO(1L, "Ótimo post!");
        Comentario comentarioSalvo = new Comentario(1L, "Ótimo post!", autor, publicacao);

        when(publicacaoRepo.findById(1L)).thenReturn(Optional.of(publicacao));
        when(usuarioRepo.findById(1L)).thenReturn(Optional.of(autor));
        when(comentarioRepo.save(any(Comentario.class))).thenReturn(comentarioSalvo);

        ComentarioResponseDTO result = interacaoService.adicionarComentario(1L, dto);

        assertNotNull(result);
        assertEquals("Ana Paula", result.nomeAutor());
        assertEquals("Ótimo post!", result.texto());
    }

    @Test
    @DisplayName("Deve dar like em publicação existente")
    void deveDarLikePublicacao() {
        when(publicacaoRepo.findById(1L)).thenReturn(Optional.of(publicacao));

        interacaoService.darLikePublicacao(1L);

        assertEquals(1, publicacao.getCurtidas());
        verify(publicacaoRepo).save(publicacao);
    }

    @Test
    @DisplayName("Deve dar like em comentário existente")
    void deveDarLikeComentario() {
        Comentario comentario = new Comentario(1L, "Comentário", autor, publicacao);
        when(comentarioRepo.findById(1L)).thenReturn(Optional.of(comentario));

        interacaoService.darLikeComentario(1L);

        assertEquals(1, comentario.getCurtidas());
        verify(comentarioRepo).save(comentario);
    }
}
