package ufg.IntegraUFG.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ufg.IntegraUFG.dto.request.LoginRequestDTO;
import ufg.IntegraUFG.dto.request.UsuarioRequestDTO;
import ufg.IntegraUFG.dto.response.UsuarioResponseDTO;
import ufg.IntegraUFG.service.UsuarioService;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    // 1. Cadastrar (POST)
    @PostMapping("/cadastrar")
    public ResponseEntity<?> cadastrar(@RequestBody UsuarioRequestDTO dto) {
        try {
            UsuarioResponseDTO novoUsuario = usuarioService.cadastrar(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(novoUsuario);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // 2. Login (POST)
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO dto) {
        try {
            UsuarioResponseDTO usuarioLogado = usuarioService.login(dto.email(), dto.senha());
            return ResponseEntity.ok(usuarioLogado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage()); // 401 Não Autorizado
        }
    }

    // 3. Buscar Perfil (GET)
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPerfil(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(usuarioService.buscarPorId(id));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build(); // 404 Não Encontrado
        }
    }

    // 4. Atualizar Perfil (PUT)
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarPerfil(@PathVariable Long id, @RequestBody UsuarioRequestDTO dto) {
        try {
            UsuarioResponseDTO usuarioAtualizado = usuarioService.atualizarConta(id, dto);
            return ResponseEntity.ok(usuarioAtualizado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // 5. Excluir Conta (DELETE)
    @DeleteMapping("/{id}")
    public ResponseEntity<?> excluirConta(@PathVariable Long id) {
        try {
            usuarioService.excluirConta(id);
            return ResponseEntity.noContent().build(); // 204 Sem Conteúdo
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}