package ufg.IntegraUFG.service;

import org.springframework.stereotype.Service;
import ufg.IntegraUFG.dto.request.UsuarioRequestDTO;
import ufg.IntegraUFG.dto.response.UsuarioResponseDTO;
import ufg.IntegraUFG.model.Usuario;
import ufg.IntegraUFG.repository.UsuarioRepository;

@Service
public class UsuarioService {

    private final UsuarioRepository repository;

    public UsuarioService(UsuarioRepository repository) {
        this.repository = repository;
    }

    // --- CADASTRO ---
    public UsuarioResponseDTO cadastrar(UsuarioRequestDTO dto) {
        if (!dto.emailInstitucional().endsWith("@ufg.br") &&
                !dto.emailInstitucional().endsWith("@discente.ufg.br")) {
            throw new IllegalArgumentException("Apenas e-mails institucionais da UFG são permitidos.");
        }

        if (repository.findByEmailInstitucional(dto.emailInstitucional()).isPresent()) {
            throw new IllegalArgumentException("E-mail já cadastrado na plataforma.");
        }

        Usuario usuario = new Usuario(null, dto.nome(), dto.emailInstitucional(), dto.senha(), dto.curso());
        Usuario salvo = repository.save(usuario);
        return mapearParaDTO(salvo);
    }

    // --- LOGIN (RF2) ---
    public UsuarioResponseDTO login(String email, String senha) {
        Usuario usuario = repository.findByEmailInstitucional(email)
                .orElseThrow(() -> new IllegalArgumentException("Credenciais inválidas."));

        // Comparação simples (Num cenário real com Spring Security, usaríamos BCrypt)
        if (!usuario.getSenha().equals(senha)) {
            throw new IllegalArgumentException("Credenciais inválidas.");
        }

        return mapearParaDTO(usuario);
    }

    // --- BUSCAR PERFIL ---
    public UsuarioResponseDTO buscarPorId(Long id) {
        Usuario usuario = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado."));
        return mapearParaDTO(usuario);
    }

    // --- ATUALIZAR CONTA (UC1) ---
    public UsuarioResponseDTO atualizarConta(Long id, UsuarioRequestDTO dto) {
        Usuario usuario = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado."));

        usuario.setNome(dto.nome());
        usuario.setCurso(dto.curso());

        // Só atualiza a senha se o utilizador enviou uma nova
        if (dto.senha() != null && !dto.senha().isBlank()) {
            usuario.setSenha(dto.senha());
        }

        Usuario salvo = repository.save(usuario);
        return mapearParaDTO(salvo);
    }

    // --- EXCLUIR CONTA (UC1) ---
    public void excluirConta(Long id) {
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("Usuário não encontrado.");
        }
        repository.deleteById(id);
    }

    // --- MÉTODO AUXILIAR ---
    private UsuarioResponseDTO mapearParaDTO(Usuario usuario) {
        return new UsuarioResponseDTO(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmailInstitucional(),
                usuario.getCurso()
        );
    }
}