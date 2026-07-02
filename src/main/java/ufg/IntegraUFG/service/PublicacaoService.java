package ufg.IntegraUFG.service;

import org.springframework.stereotype.Service;
import ufg.IntegraUFG.dto.request.PostagemRequestDTO;
import ufg.IntegraUFG.dto.response.PostagemResponseDTO;
import ufg.IntegraUFG.model.PostagemTexto;
import ufg.IntegraUFG.model.Usuario;
import ufg.IntegraUFG.repository.PostagemTextoRepository;
import ufg.IntegraUFG.repository.UsuarioRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PublicacaoService {
    private final PostagemTextoRepository postagemRepository;
    private final UsuarioRepository usuarioRepository;

    public PublicacaoService(PostagemTextoRepository postagemRepository, UsuarioRepository usuarioRepository) {
        this.postagemRepository = postagemRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public PostagemResponseDTO criarPostagemTexto(PostagemRequestDTO dto) {
        // 1. Procurar o utilizador na base de dados
        Usuario autor = usuarioRepository.findById(dto.idAutor())
                .orElseThrow(() -> new IllegalArgumentException("Utilizador não encontrado."));

        // 2. Instanciar a entidade
        PostagemTexto postagem = new PostagemTexto(null, autor, dto.conteudo());

        // 3. Guardar na base de dados
        PostagemTexto postagemGuardada = postagemRepository.save(postagem);

        // 4. Converter para DTO e devolver
        return mapearParaDTO(postagemGuardada);
    }

    public List<PostagemResponseDTO> listarPostagensTexto() {
        // Retorna a lista de todas as postagens, já convertidas para DTO para não expor as passwords dos autores
        return postagemRepository.findAll().stream()
                .map(this::mapearParaDTO)
                .collect(Collectors.toList());
    }

    // Método auxiliar para não repetirmos código
    private PostagemResponseDTO mapearParaDTO(PostagemTexto postagem) {
        return new PostagemResponseDTO(
                postagem.getId(),
                postagem.getAutor().getNome(),
                postagem.getAutor().getCurso(),
                postagem.getConteudo(),
                postagem.getDataCriacao(),
                postagem.getCurtidas()
        );
    }

}
