package ufg.IntegraUFG.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ufg.IntegraUFG.dto.request.EventoRequestDTO;
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

    // Criar Postagem Texto
    @PostMapping("/texto")
    public ResponseEntity<?> criarPostagemTexto(@RequestBody PostagemRequestDTO dto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(publicacaoService.criarPostagemTexto(dto));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Listar Postagens Texto
    @GetMapping("/texto")
    public ResponseEntity<List<PostagemResponseDTO>> listarPostagensTexto() {
        return ResponseEntity.ok(publicacaoService.listarPostagensTexto());
    }

    // NOVO: Editar Postagem Texto
    @PutMapping("/texto/{id}")
    public ResponseEntity<?> editarPostagem(@PathVariable Long id, @RequestBody PostagemRequestDTO dto) {
        try {
            return ResponseEntity.ok(publicacaoService.atualizarPostagemTexto(id, dto));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Excluir Postagem Texto
    @DeleteMapping("/texto/{id}")
    public ResponseEntity<?> deletarPostagemTexto(@PathVariable Long id) {
        try {
            publicacaoService.deletarPostagemTexto(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // NOVO: Criar Evento Acadêmico
    @PostMapping("/evento")
    public ResponseEntity<?> criarEventoAcademico(@RequestBody EventoRequestDTO dto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(publicacaoService.criarEvento(dto));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}