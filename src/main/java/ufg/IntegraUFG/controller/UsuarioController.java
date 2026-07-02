package ufg.IntegraUFG.controller;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ufg.IntegraUFG.dto.request.UsuarioRequestDTO;
import ufg.IntegraUFG.model.Usuario;
import ufg.IntegraUFG.service.UsuarioService;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<?> cadastrar(@RequestBody UsuarioRequestDTO dto) {
        try {
            Usuario novoUsuario = new Usuario(null, dto.nome(), dto.emailInstitucional(), dto.senha(), dto.curso());
            usuarioService.cadastrar(novoUsuario);
            return ResponseEntity.status(HttpStatus.CREATED).body("Usuário cadastrado com sucesso!");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage()); // Exibe o erro do e-mail da UFG
        }
    }
}