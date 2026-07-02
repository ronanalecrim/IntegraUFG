package ufg.IntegraUFG.controller;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ufg.IntegraUFG.dto.request.ComentarioRequestDTO;
import ufg.IntegraUFG.model.Comentario;
import ufg.IntegraUFG.model.Publicacao;
import ufg.IntegraUFG.model.Usuario;
import ufg.IntegraUFG.repository.ComentarioRepository;
import ufg.IntegraUFG.repository.PublicacaoRepository;
import ufg.IntegraUFG.repository.UsuarioRepository;

@RestController
@RequestMapping("/api")
public class InteracaoController {

    private final PublicacaoRepository publicacaoRepo;
    private final ComentarioRepository comentarioRepo;
    private final UsuarioRepository usuarioRepo;

    public InteracaoController(PublicacaoRepository pRepo, ComentarioRepository cRepo, UsuarioRepository uRepo) {
        this.publicacaoRepo = pRepo;
        this.comentarioRepo = cRepo;
        this.usuarioRepo = uRepo;
    }

    // Comentar numa publicação
    @PostMapping("/publicacoes/{id}/comentarios")
    public ResponseEntity<?> comentar(@PathVariable Long id, @RequestBody ComentarioRequestDTO dto) {
        Publicacao pub = publicacaoRepo.findById(id).orElse(null);
        Usuario autor = usuarioRepo.findById(dto.idAutor()).orElse(null);

        if(pub == null || autor == null) return ResponseEntity.badRequest().body("Publicação ou Usuário inválido.");

        Comentario comentario = new Comentario(null, dto.texto(), autor, pub);
        comentarioRepo.save(comentario);
        return ResponseEntity.status(HttpStatus.CREATED).body("Comentário adicionado!");
    }

    // Curtir Publicação
    @PostMapping("/publicacoes/{id}/curtir")
    public ResponseEntity<?> curtirPublicacao(@PathVariable Long id) {
        return publicacaoRepo.findById(id).map(pub -> {
            pub.curtir();
            publicacaoRepo.save(pub);
            return ResponseEntity.ok("Publicação curtida! Total: " + pub.getTotalCurtidas());
        }).orElse(ResponseEntity.badRequest().body("Publicação não encontrada."));
    }

    // Curtir Comentário
    @PostMapping("/comentarios/{id}/curtir")
    public ResponseEntity<?> curtirComentario(@PathVariable Long id) {
        return comentarioRepo.findById(id).map(comentario -> {
            comentario.curtir();
            comentarioRepo.save(comentario);
            return ResponseEntity.ok("Comentário curtido! Total: " + comentario.getTotalCurtidas());
        }).orElse(ResponseEntity.badRequest().body("Comentário não encontrado."));
    }
}