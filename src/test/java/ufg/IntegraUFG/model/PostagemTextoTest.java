package ufg.IntegraUFG.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("PostagemTexto - Testes do Modelo")
class PostagemTextoTest {

    private Usuario autor;
    private PostagemTexto postagem;

    @BeforeEach
    void setUp() {
        autor = new Usuario(1L, "João", "joao@ufg.br", "senha", "CC");
        postagem = new PostagemTexto(1L, autor, "Conteúdo da postagem");
    }

    @Test
    @DisplayName("Deve criar postagem com construtor parametrizado")
    void deveCriarPostagem() {
        assertEquals(1L, postagem.getId());
        assertSame(autor, postagem.getAutor());
        assertEquals("Conteúdo da postagem", postagem.getConteudo());
        assertEquals(0, postagem.getCurtidas());
        assertNotNull(postagem.getDataCriacao());
    }

    @Test
    @DisplayName("Deve incrementar curtidas ao curtir")
    void deveIncrementarCurtidas() {
        postagem.curtir();
        postagem.curtir();

        assertEquals(2, postagem.getTotalCurtidas());
    }

    @Test
    @DisplayName("Deve adicionar comentário bidireccionalmente")
    void deveAdicionarComentario() {
        Comentario comentario = new Comentario(1L, "Um comentário", autor, null);

        postagem.adicionarComentario(comentario);

        assertEquals(1, postagem.getComentarios().size());
        assertSame(postagem, comentario.getPublicacao());
    }
}
