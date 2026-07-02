package ufg.IntegraUFG.service;

import org.springframework.stereotype.Service;
import ufg.IntegraUFG.dto.request.EventoRequestDTO;
import ufg.IntegraUFG.dto.request.PostagemRequestDTO;
import ufg.IntegraUFG.dto.response.EventoResponseDTO;
import ufg.IntegraUFG.dto.response.PostagemResponseDTO;
import ufg.IntegraUFG.model.EventoAcademico;
import ufg.IntegraUFG.model.PostagemTexto;
import ufg.IntegraUFG.model.Usuario;
import ufg.IntegraUFG.repository.EventoAcademicoRepository;
import ufg.IntegraUFG.repository.PostagemTextoRepository;
import ufg.IntegraUFG.repository.UsuarioRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PublicacaoService {
    private final PostagemTextoRepository postagemRepository;
    private final EventoAcademicoRepository eventoRepository;
    private final UsuarioRepository usuarioRepository;

    public PublicacaoService(PostagemTextoRepository postagemRepository, EventoAcademicoRepository eventoRepository, UsuarioRepository usuarioRepository) {
        this.postagemRepository = postagemRepository;
        this.eventoRepository = eventoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    // --- MÉTODOS DE POSTAGEM DE TEXTO ---
    public PostagemResponseDTO criarPostagemTexto(PostagemRequestDTO dto) {
        Usuario autor = usuarioRepository.findById(dto.idAutor())
                .orElseThrow(() -> new IllegalArgumentException("Utilizador não encontrado."));

        PostagemTexto postagem = new PostagemTexto(null, autor, dto.conteudo());
        PostagemTexto postagemGuardada = postagemRepository.save(postagem);
        return mapearParaDTO(postagemGuardada);
    }

    public List<PostagemResponseDTO> listarPostagensTexto() {
        return postagemRepository.findAll().stream()
                .map(this::mapearParaDTO)
                .collect(Collectors.toList());
    }

    // NOVO: EDITAR POSTAGEM
    public PostagemResponseDTO atualizarPostagemTexto(Long id, PostagemRequestDTO dto) {
        PostagemTexto postagem = postagemRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Publicação não encontrada."));

        if (!postagem.getAutor().getId().equals(dto.idAutor())) {
            throw new IllegalArgumentException("Acesso negado: Apenas o autor pode editar.");
        }

        postagem.setConteudo(dto.conteudo());
        PostagemTexto postagemAtualizada = postagemRepository.save(postagem);
        return mapearParaDTO(postagemAtualizada);
    }

    public void deletarPostagemTexto(Long id) {
        if (!postagemRepository.existsById(id)) {
            throw new IllegalArgumentException("Publicação não encontrada.");
        }
        postagemRepository.deleteById(id);
    }

    // --- MÉTODOS DE EVENTO ACADÊMICO ---
    public EventoResponseDTO criarEvento(EventoRequestDTO dto) {
        Usuario autor = usuarioRepository.findById(dto.idAutor())
                .orElseThrow(() -> new IllegalArgumentException("Utilizador não encontrado."));

        EventoAcademico evento = new EventoAcademico(null, autor, dto.titulo(), dto.descricao(), dto.dataEvento(), dto.local());
        EventoAcademico eventoSalvo = eventoRepository.save(evento);

        return new EventoResponseDTO(
                eventoSalvo.getId(),
                autor.getNome(),
                autor.getCurso(),
                eventoSalvo.getTitulo(),
                eventoSalvo.getDescricao(),
                eventoSalvo.getDataEvento(),
                eventoSalvo.getLocal(),
                eventoSalvo.getCurtidas()
        );
    }

    // --- AUXILIAR ---
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
