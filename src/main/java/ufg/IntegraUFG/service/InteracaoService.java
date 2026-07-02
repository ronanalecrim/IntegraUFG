package ufg.IntegraUFG.service;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ufg.IntegraUFG.dto.request.ComentarioRequestDTO;
import ufg.IntegraUFG.dto.response.ComentarioResponseDTO;
import ufg.IntegraUFG.model.Comentario;
import ufg.IntegraUFG.model.Publicacao;
import ufg.IntegraUFG.model.Usuario;
import ufg.IntegraUFG.repository.ComentarioRepository;
import ufg.IntegraUFG.repository.PublicacaoRepository;
import ufg.IntegraUFG.repository.UsuarioRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InteracaoService {

    private final PublicacaoRepository publicacaoRepo;
    private final ComentarioRepository comentarioRepo;
    private final UsuarioRepository usuarioRepo;

    public InteracaoService(PublicacaoRepository publicacaoRepo, ComentarioRepository comentarioRepo, UsuarioRepository usuarioRepo) {
        this.publicacaoRepo = publicacaoRepo;
        this.comentarioRepo = comentarioRepo;
        this.usuarioRepo = usuarioRepo;
    }

    // --- RF4: ADICIONAR COMENTÁRIO ---
    @Transactional
    public ComentarioResponseDTO adicionarComentario(Long idPublicacao, ComentarioRequestDTO dto) {
        Publicacao pub = publicacaoRepo.findById(idPublicacao)
                .orElseThrow(() -> new IllegalArgumentException("Publicação não encontrada."));
        Usuario autor = usuarioRepo.findById(dto.idAutor())
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado."));

        Comentario comentario = new Comentario(null, dto.texto(), autor, pub);
        pub.adicionarComentario(comentario);

        Comentario salvo = comentarioRepo.save(comentario);

        return new ComentarioResponseDTO(
                salvo.getId(),
                autor.getNome(),
                salvo.getTexto(),
                salvo.getDataComentario(),
                salvo.getTotalCurtidas()
        );
    }

    // --- LISTAR COMENTÁRIOS DE UMA PUBLICAÇÃO ---
    public List<ComentarioResponseDTO> listarComentarios(Long idPublicacao) {
        Publicacao pub = publicacaoRepo.findById(idPublicacao)
                .orElseThrow(() -> new IllegalArgumentException("Publicação não encontrada."));

        return pub.getComentarios().stream()
                .map(c -> new ComentarioResponseDTO(
                        c.getId(),
                        c.getAutor().getNome(),
                        c.getTexto(),
                        c.getDataComentario(),
                        c.getTotalCurtidas()
                ))
                .collect(Collectors.toList());
    }

    // --- RF5: DAR LIKE (PUBLICAÇÃO) ---
    @Transactional
    public void darLikePublicacao(Long id) {
        Publicacao pub = publicacaoRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Publicação não encontrada."));
        pub.curtir();
        publicacaoRepo.save(pub);
    }

    // --- RF5: DAR LIKE (COMENTÁRIO) ---
    @Transactional
    public void darLikeComentario(Long id) {
        Comentario c = comentarioRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Comentário não encontrado."));
        c.curtir();
        comentarioRepo.save(c);
    }
}