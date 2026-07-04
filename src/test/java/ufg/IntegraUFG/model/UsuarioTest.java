package ufg.IntegraUFG.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Usuario - Testes do Modelo")
class UsuarioTest {

    private Usuario usuario;

    @BeforeEach
    void setUp() {
        usuario = new Usuario(1L, "João Silva", "joao@ufg.br", "senha123", "Ciência da Computação");
    }

    @Test
    @DisplayName("Deve criar usuário com todos os campos via construtor")
    void deveCriarUsuario() {
        assertEquals(1L, usuario.getId());
        assertEquals("João Silva", usuario.getNome());
        assertEquals("joao@ufg.br", usuario.getEmailInstitucional());
        assertEquals("senha123", usuario.getSenha());
        assertEquals("Ciência da Computação", usuario.getCurso());
        assertTrue(usuario.getPublicacoes().isEmpty());
        assertTrue(usuario.getComentarios().isEmpty());
    }

    @Test
    @DisplayName("Deve adicionar publicação e definir autor bidireccionalmente")
    void deveAdicionarPublicacao() {
        PostagemTexto postagem = new PostagemTexto(1L, null, "Conteúdo teste");

        usuario.adicionarPublicacao(postagem);

        assertEquals(1, usuario.getPublicacoes().size());
        assertSame(usuario, postagem.getAutor());
    }

    @Test
    @DisplayName("Deve adicionar comentário e definir autor bidireccionalmente")
    void deveAdicionarComentario() {
        PostagemTexto postagem = new PostagemTexto(1L, usuario, "Conteúdo");
        Comentario comentario = new Comentario(1L, "Comentário teste", null, postagem);

        usuario.adicionarComentario(comentario);

        assertEquals(1, usuario.getComentarios().size());
        assertSame(usuario, comentario.getAutor());
    }
}
