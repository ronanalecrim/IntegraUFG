package ufg.IntegraUFG.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ufg.IntegraUFG.dto.request.PostagemRequestDTO;
import ufg.IntegraUFG.dto.response.PostagemResponseDTO;
import ufg.IntegraUFG.service.PublicacaoService;

import java.util.List;

@RestController
@RequestMapping("/api/publicacoes")
public class PublicacaoController {

    private final PublicacaoService publicacaoService;

    public PublicacaoController(PublicacaoService publicacaoService) {
        this.publicacaoService = publicacaoService;
    }


    // POST: http://localhost:8080/api/publicacoes/texto
    @PostMapping("/texto")
    public ResponseEntity<PostagemResponseDTO> criarPostagemTexto(@RequestBody PostagemRequestDTO dto) {
        try {
            PostagemResponseDTO novaPostagem = publicacaoService.criarPostagemTexto(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(novaPostagem);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build(); // Retorna Erro 400 se o utilizador não existir
        }
    }

    // GET: http://localhost:8080/api/publicacoes/texto
    @GetMapping("/texto")
    public ResponseEntity<List<PostagemResponseDTO>> listarPostagensTexto() {
        List<PostagemResponseDTO> postagens = publicacaoService.listarPostagensTexto();
        return ResponseEntity.ok(postagens);
    }

    // DELETE: http://localhost:8080/api/publicacoes/texto/{id}
    @DeleteMapping("/texto/{id}")
    public ResponseEntity<?> deletarPostagemTexto(@PathVariable Long id) {
        try {
            publicacaoService.deletarPostagemTexto(id);
            // Retorna 204 No Content, que é o padrão HTTP correto para um Delete bem-sucedido
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}