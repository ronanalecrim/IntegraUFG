package ufg.IntegraUFG.controller;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ufg.IntegraUFG.dto.request.ComentarioRequestDTO;
import ufg.IntegraUFG.service.InteracaoService;

@RestController
@RequestMapping("/api")
public class InteracaoController {

    private final InteracaoService interacaoService;


    public InteracaoController(InteracaoService interacaoService) {
        this.interacaoService = interacaoService;
    }

    // RF4: Comentar numa publicação
    @PostMapping("/publicacoes/{id}/comentarios")
    public ResponseEntity<?> comentar(@PathVariable Long id, @RequestBody ComentarioRequestDTO dto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(interacaoService.adicionarComentario(id, dto));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Listar Comentários de uma Publicação
    @GetMapping("/publicacoes/{id}/comentarios")
    public ResponseEntity<?> listarComentarios(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(interacaoService.listarComentarios(id));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // RF5: Curtir Publicação
    @PostMapping("/publicacoes/{id}/curtir")
    public ResponseEntity<?> curtirPublicacao(@PathVariable Long id) {
        try {
            interacaoService.darLikePublicacao(id);
            return ResponseEntity.ok().body("Publicação curtida com sucesso!");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // RF5: Curtir Comentário
    @PostMapping("/comentarios/{id}/curtir")
    public ResponseEntity<?> curtirComentario(@PathVariable Long id) {
        try {
            interacaoService.darLikeComentario(id);
            return ResponseEntity.ok().body("Comentário curtido com sucesso!");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}