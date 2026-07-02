package ufg.IntegraUFG.service;

import org.springframework.stereotype.Service;
import ufg.IntegraUFG.model.Usuario;
import ufg.IntegraUFG.repository.UsuarioRepository;

@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Usuario cadastrar(Usuario usuario) {
        // Validação de Domínio UFG
        if (!usuario.getEmailInstitucional().endsWith("@ufg.br") &&
                !usuario.getEmailInstitucional().endsWith("@discente.ufg.br")) {
            throw new IllegalArgumentException("Apenas e-mails institucionais da UFG são permitidos.");
        }

        // Verifica se já existe
        if (usuarioRepository.findByEmailInstitucional(usuario.getEmailInstitucional()).isPresent()) {
            throw new IllegalArgumentException("E-mail já cadastrado na plataforma.");
        }

        return usuarioRepository.save(usuario);
    }
}
