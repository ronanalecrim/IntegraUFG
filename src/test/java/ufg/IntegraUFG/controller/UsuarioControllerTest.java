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
import ufg.IntegraUFG.dto.request.LoginRequestDTO;
import ufg.IntegraUFG.dto.request.UsuarioRequestDTO;
import ufg.IntegraUFG.dto.response.UsuarioResponseDTO;
import ufg.IntegraUFG.service.UsuarioService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UsuarioController.class)
@Import(SecurityConfig.class)
@DisplayName("UsuarioController - Testes de Endpoint")
class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private UsuarioService usuarioService;

    @Test
    @DisplayName("POST /api/usuarios/cadastrar deve retornar 201")
    void deveCadastrar() throws Exception {
        UsuarioRequestDTO dto = new UsuarioRequestDTO("João", "joao@ufg.br", "senha123", "CC");
        UsuarioResponseDTO response = new UsuarioResponseDTO(1L, "João", "joao@ufg.br", "CC");

        when(usuarioService.cadastrar(any())).thenReturn(response);

        mockMvc.perform(post("/api/usuarios/cadastrar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nome").value("João"))
                .andExpect(jsonPath("$.emailInstitucional").value("joao@ufg.br"));
    }

    @Test
    @DisplayName("POST /api/usuarios/login deve retornar 200")
    void deveLogin() throws Exception {
        LoginRequestDTO dto = new LoginRequestDTO("joao@ufg.br", "senha123");
        UsuarioResponseDTO response = new UsuarioResponseDTO(1L, "João", "joao@ufg.br", "CC");

        when(usuarioService.login("joao@ufg.br", "senha123")).thenReturn(response);

        mockMvc.perform(post("/api/usuarios/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    @DisplayName("POST /api/usuarios/cadastrar com dados inválidos deve retornar 400")
    void deveRetornar400() throws Exception {
        UsuarioRequestDTO dto = new UsuarioRequestDTO("João", "joao@gmail.com", "senha123", "CC");

        when(usuarioService.cadastrar(any()))
                .thenThrow(new IllegalArgumentException("Apenas e-mails institucionais da UFG são permitidos."));

        mockMvc.perform(post("/api/usuarios/cadastrar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());
    }
}
