package ufg.IntegraUFG.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ufg.IntegraUFG.dto.request.EventoRequestDTO;
import ufg.IntegraUFG.dto.request.PostagemRequestDTO;
import ufg.IntegraUFG.dto.response.EventoResponseDTO;
import ufg.IntegraUFG.dto.response.PostagemResponseDTO;
import ufg.IntegraUFG.model.EventoAcademico;
import ufg.IntegraUFG.model.PostagemTexto;
import ufg.IntegraUFG.model.Usuario;
import ufg.IntegraUFG.repository.EventoAcademicoRepository;
import ufg.IntegraUFG.repository.PostagemTextoRepository;
import ufg.IntegraUFG.repository.UsuarioRepository;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("PublicacaoService - Testes Unitários")
class PublicacaoServiceTest {

    @Mock
    private PostagemTextoRepository postagemRepository;

    @Mock
    private EventoAcademicoRepository eventoRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private PublicacaoService publicacaoService;

    private Usuario autor;

    @BeforeEach
    void setUp() {
        autor = new Usuario(1L, "João Silva", "joao@ufg.br", "hashed", "CC");
    }

    @Test
    @DisplayName("Deve criar postagem de texto com sucesso")
    void deveCriarPostagem() {
        PostagemRequestDTO dto = new PostagemRequestDTO(1L, "Conteúdo do post");
        PostagemTexto postSalva = new PostagemTexto(1L, autor, "Conteúdo do post");

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(autor));
        when(postagemRepository.save(any(PostagemTexto.class))).thenReturn(postSalva);

        PostagemResponseDTO result = publicacaoService.criarPostagemTexto(dto);

        assertNotNull(result);
        assertEquals("João Silva", result.nomeAutor());
        assertEquals("Conteúdo do post", result.conteudo());
    }

    @Test
    @DisplayName("Deve negar edição de postagem por autor diferente")
    void deveNegarEdicaoPorOutroAutor() {
        PostagemTexto postExistente = new PostagemTexto(1L, autor, "Conteúdo antigo");
        PostagemRequestDTO dto = new PostagemRequestDTO(2L, "Novo conteúdo");

        when(postagemRepository.findById(1L)).thenReturn(Optional.of(postExistente));

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> publicacaoService.atualizarPostagemTexto(1L, dto));
        assertEquals("Acesso negado: Apenas o autor pode editar.", ex.getMessage());
    }

    @Test
    @DisplayName("Deve criar evento acadêmico com sucesso")
    void deveCriarEvento() {
        LocalDateTime dataEvento = LocalDateTime.of(2026, 8, 15, 14, 0);
        EventoRequestDTO dto = new EventoRequestDTO(1L, "Semana da Computação", "Palestras", dataEvento, "Auditório");
        EventoAcademico eventoSalvo = new EventoAcademico(1L, autor, "Semana da Computação", "Palestras", dataEvento, "Auditório");

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(autor));
        when(eventoRepository.save(any(EventoAcademico.class))).thenReturn(eventoSalvo);

        EventoResponseDTO result = publicacaoService.criarEvento(dto);

        assertNotNull(result);
        assertEquals("Semana da Computação", result.titulo());
        assertEquals("Auditório", result.local());
    }

    @Test
    @DisplayName("Deve deletar postagem existente")
    void deveDeletarPostagem() {
        when(postagemRepository.existsById(1L)).thenReturn(true);

        assertDoesNotThrow(() -> publicacaoService.deletarPostagemTexto(1L));
        verify(postagemRepository).deleteById(1L);
    }
}
