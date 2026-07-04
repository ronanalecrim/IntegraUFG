package ufg.IntegraUFG.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ufg.IntegraUFG.config.SecurityConfig;
import ufg.IntegraUFG.dto.request.ComentarioRequestDTO;
import ufg.IntegraUFG.dto.response.ComentarioResponseDTO;
import ufg.IntegraUFG.service.InteracaoService;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(InteracaoController.class)
@Import(SecurityConfig.class)
@DisplayName("InteracaoController - Testes de Endpoint")
class InteracaoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private InteracaoService interacaoService;

    @Test
    @DisplayName("POST /api/publicacoes/{id}/comentarios deve retornar 201")
    void deveComentar() throws Exception {
        ComentarioRequestDTO dto = new ComentarioRequestDTO(1L, "Ótimo post!");
        ComentarioResponseDTO response = new ComentarioResponseDTO(1L, "Ana", "Ótimo post!", LocalDateTime.now(), 0);

        when(interacaoService.adicionarComentario(eq(1L), any())).thenReturn(response);

        mockMvc.perform(post("/api/publicacoes/1/comentarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.texto").value("Ótimo post!"));
    }

    @Test
    @DisplayName("POST /api/publicacoes/{id}/curtir deve retornar 200")
    void deveCurtirPublicacao() throws Exception {
        doNothing().when(interacaoService).darLikePublicacao(1L);

        mockMvc.perform(post("/api/publicacoes/1/curtir"))
                .andExpect(status().isOk());
    }
}
