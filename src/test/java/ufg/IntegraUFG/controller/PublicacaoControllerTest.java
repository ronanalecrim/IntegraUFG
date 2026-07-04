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
import ufg.IntegraUFG.dto.request.PostagemRequestDTO;
import ufg.IntegraUFG.dto.response.PostagemResponseDTO;
import ufg.IntegraUFG.service.PublicacaoService;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PublicacaoController.class)
@Import(SecurityConfig.class)
@DisplayName("PublicacaoController - Testes de Endpoint")
class PublicacaoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private PublicacaoService publicacaoService;

    @Test
    @DisplayName("POST /api/publicacoes/texto deve retornar 201")
    void deveCriarPostagem() throws Exception {
        PostagemRequestDTO dto = new PostagemRequestDTO(1L, "Meu post");
        PostagemResponseDTO response = new PostagemResponseDTO(1L, "João", "CC", "Meu post", LocalDateTime.now(), 0);

        when(publicacaoService.criarPostagemTexto(any())).thenReturn(response);

        mockMvc.perform(post("/api/publicacoes/texto")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.conteudo").value("Meu post"));
    }

    @Test
    @DisplayName("GET /api/publicacoes/texto deve retornar 200 com lista")
    void deveListarPostagens() throws Exception {
        PostagemResponseDTO p1 = new PostagemResponseDTO(1L, "João", "CC", "Post 1", LocalDateTime.now(), 0);
        when(publicacaoService.listarPostagensTexto()).thenReturn(List.of(p1));

        mockMvc.perform(get("/api/publicacoes/texto"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].conteudo").value("Post 1"));
    }
}
