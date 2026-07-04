package ufg.IntegraUFG.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Comentario - Testes do Modelo")
class ComentarioTest {

    private Usuario autor;
    private PostagemTexto publicacao;
    private Comentario comentario;

    @BeforeEach
    void setUp() {
        autor = new Usuario(1L, "Ana Paula", "ana@ufg.br", "senha", "Matemática");
        publicacao = new PostagemTexto(1L, autor, "Post original");
        comentario = new Comentario(1L, "Ótimo post!", autor, publicacao);
    }

    @Test
    @DisplayName("Deve criar comentário com todos os campos")
    void deveCriarComentario() {
        assertEquals(1L, comentario.getId());
        assertEquals("Ótimo post!", comentario.getTexto());
        assertSame(autor, comentario.getAutor());
        assertSame(publicacao, comentario.getPublicacao());
        assertNotNull(comentario.getDataComentario());
        assertEquals(0, comentario.getCurtidas());
    }

    @Test
    @DisplayName("Deve implementar Interagivel e incrementar curtidas")
    void deveIncrementarCurtidas() {
        assertInstanceOf(Interagivel.class, comentario);

        comentario.curtir();
        comentario.curtir();

        assertEquals(2, comentario.getTotalCurtidas());
        assertEquals(comentario.getCurtidas(), comentario.getTotalCurtidas());
    }
}
