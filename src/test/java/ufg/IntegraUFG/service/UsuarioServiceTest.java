package ufg.IntegraUFG.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import ufg.IntegraUFG.dto.request.UsuarioRequestDTO;
import ufg.IntegraUFG.dto.response.UsuarioResponseDTO;
import ufg.IntegraUFG.model.Usuario;
import ufg.IntegraUFG.repository.UsuarioRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("UsuarioService - Testes Unitários")
class UsuarioServiceTest {

    @Mock
    private UsuarioRepository repository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UsuarioService usuarioService;

    private Usuario usuario;

    @BeforeEach
    void setUp() {
        usuario = new Usuario(1L, "João Silva", "joao@ufg.br", "hashedSenha", "Ciência da Computação");
    }

    @Test
    @DisplayName("Deve cadastrar usuário com e-mail institucional válido")
    void deveCadastrar() {
        UsuarioRequestDTO dto = new UsuarioRequestDTO("João", "joao@ufg.br", "senha123", "CC");

        when(repository.findByEmailInstitucional("joao@ufg.br")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("senha123")).thenReturn("hashedSenha");
        when(repository.save(any(Usuario.class))).thenReturn(usuario);

        UsuarioResponseDTO result = usuarioService.cadastrar(dto);

        assertNotNull(result);
        assertEquals("João Silva", result.nome());
        assertEquals("joao@ufg.br", result.emailInstitucional());
        verify(passwordEncoder).encode("senha123");
    }

    @Test
    @DisplayName("Deve rejeitar e-mail não-institucional")
    void deveRejeitarEmailNaoInstitucional() {
        UsuarioRequestDTO dto = new UsuarioRequestDTO("João", "joao@gmail.com", "senha123", "CC");

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> usuarioService.cadastrar(dto));
        assertEquals("Apenas e-mails institucionais da UFG são permitidos.", ex.getMessage());
    }

    @Test
    @DisplayName("Deve fazer login com credenciais corretas")
    void deveFazerLogin() {
        when(repository.findByEmailInstitucional("joao@ufg.br")).thenReturn(Optional.of(usuario));
        when(passwordEncoder.matches("senha123", "hashedSenha")).thenReturn(true);

        UsuarioResponseDTO result = usuarioService.login("joao@ufg.br", "senha123");

        assertNotNull(result);
        assertEquals(1L, result.id());
        assertEquals("João Silva", result.nome());
    }

    @Test
    @DisplayName("Deve rejeitar login com senha incorreta")
    void deveRejeitarSenhaIncorreta() {
        when(repository.findByEmailInstitucional("joao@ufg.br")).thenReturn(Optional.of(usuario));
        when(passwordEncoder.matches("errada", "hashedSenha")).thenReturn(false);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> usuarioService.login("joao@ufg.br", "errada"));
        assertEquals("Credenciais inválidas.", ex.getMessage());
    }

    @Test
    @DisplayName("Deve excluir conta existente")
    void deveExcluirConta() {
        when(repository.existsById(1L)).thenReturn(true);

        assertDoesNotThrow(() -> usuarioService.excluirConta(1L));
        verify(repository).deleteById(1L);
    }
}
