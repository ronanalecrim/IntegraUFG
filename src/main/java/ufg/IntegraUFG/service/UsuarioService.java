package ufg.IntegraUFG.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ufg.IntegraUFG.dto.request.UsuarioRequestDTO;
import ufg.IntegraUFG.dto.response.UsuarioResponseDTO;
import ufg.IntegraUFG.model.Usuario;
import ufg.IntegraUFG.repository.UsuarioRepository;

@Service
public class UsuarioService {

    private final UsuarioRepository repository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    // --- CADASTRO ---
    public UsuarioResponseDTO cadastrar(UsuarioRequestDTO dto) {
        if (dto.emailInstitucional() == null || dto.emailInstitucional().isBlank()) {
            throw new IllegalArgumentException("O e-mail não pode ser vazio.");
        }

        if (!dto.emailInstitucional().endsWith("@ufg.br") &&
                !dto.emailInstitucional().endsWith("@discente.ufg.br")) {
            throw new IllegalArgumentException("Apenas e-mails institucionais da UFG são permitidos.");
        }

        if (repository.findByEmailInstitucional(dto.emailInstitucional()).isPresent()) {
            throw new IllegalArgumentException("E-mail já cadastrado na plataforma.");
        }

        String senhaHasheada = passwordEncoder.encode(dto.senha());

        Usuario usuario = new Usuario(null, dto.nome(), dto.emailInstitucional(), senhaHasheada, dto.curso());
        Usuario salvo = repository.save(usuario);
        return mapearParaDTO(salvo);
    }

    // --- LOGIN (RF2) ---
    public UsuarioResponseDTO login(String email, String senha) {
        Usuario usuario = repository.findByEmailInstitucional(email)
                .orElseThrow(() -> new IllegalArgumentException("Credenciais inválidas."));

        if (!passwordEncoder.matches(senha, usuario.getSenha())) {
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
            String novaSenhaHasheada = passwordEncoder.encode(dto.senha());
            usuario.setSenha(novaSenhaHasheada);
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