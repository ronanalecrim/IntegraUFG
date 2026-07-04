package ufg.IntegraUFG.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("EventoAcademico - Testes do Modelo")
class EventoAcademicoTest {

    private Usuario autor;
    private EventoAcademico evento;
    private LocalDateTime dataEvento;

    @BeforeEach
    void setUp() {
        autor = new Usuario(1L, "Prof. Carlos", "carlos@ufg.br", "senha", "Física");
        dataEvento = LocalDateTime.of(2026, 8, 15, 14, 0);
        evento = new EventoAcademico(1L, autor, "Semana de Computação", "Palestras e workshops", dataEvento, "Auditório INF");
    }

    @Test
    @DisplayName("Deve criar evento com todos os campos")
    void deveCriarEvento() {
        assertEquals(1L, evento.getId());
        assertEquals("Semana de Computação", evento.getTitulo());
        assertEquals("Palestras e workshops", evento.getDescricao());
        assertEquals(dataEvento, evento.getDataEvento());
        assertEquals("Auditório INF", evento.getLocal());
    }

    @Test
    @DisplayName("Deve ser instância de Publicacao e Interagivel")
    void deveHerdarPublicacao() {
        assertInstanceOf(Publicacao.class, evento);
        assertInstanceOf(Interagivel.class, evento);
    }

    @Test
    @DisplayName("Deve incrementar curtidas ao curtir o evento")
    void deveIncrementarCurtidas() {
        evento.curtir();
        evento.curtir();
        evento.curtir();

        assertEquals(3, evento.getTotalCurtidas());
    }
}
