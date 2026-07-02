package ufg.IntegraUFG.service;

import ufg.IntegraUFG.model.Usuario;
import ufg.IntegraUFG.repository.UserRepository;

public class UsuarioService {
    private final UserRepository userRepository;

    public UsuarioService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Usuario cadastrar(Usuario usuario) {
        // Validação de Domínio UFG
        if (!usuario.getEmailInstitucional().endsWith("@ufg.br") &&
                !usuario.getEmailInstitucional().endsWith("@discente.ufg.br")) {
            throw new IllegalArgumentException("Apenas e-mails institucionais da UFG são permitidos.");
        }

        // Verifica se já existe
        if (userRepository.findByEmailInstitucional(usuario.getEmailInstitucional()).isPresent()) {
            throw new IllegalArgumentException("E-mail já cadastrado na plataforma.");
        }

        return userRepository.save(usuario);
    }
}
