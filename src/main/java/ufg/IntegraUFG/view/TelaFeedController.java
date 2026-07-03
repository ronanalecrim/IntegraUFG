package ufg.IntegraUFG.view;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.VBox;
import ufg.IntegraUFG.client.InteracaoClient;
import ufg.IntegraUFG.client.PublicacaoClient;
import ufg.IntegraUFG.view.components.PostCardComponent;
import ufg.IntegraUFG.view.components.EventoCardComponent;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class TelaFeedController {

    @FXML
    private VBox feedContainer;

    // Campos da Aba de Postagem
    @FXML
    private TextArea campoNovaPostagem;

    // Campos da Aba de Evento
    @FXML
    private TextField campoEventoTitulo;
    @FXML
    private TextArea campoEventoDescricao;
    @FXML
    private DatePicker campoEventoData;
    @FXML
    private TextField campoEventoLocal;

    private final PublicacaoClient publicacaoClient = new PublicacaoClient();
    private final InteracaoClient interacaoClient = new InteracaoClient();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @FXML
    public void initialize() {
        carregarFeed();
    }

    private void carregarFeed() {
        try {
            String jsonRespostaTextos = publicacaoClient.buscarFeed();
            String jsonRespostaEventos = publicacaoClient.buscarEventos();

            // Limpa o feed (removendo todos, menos o primeiro que é o TabPane de criação)
            if (feedContainer.getChildren().size() > 1) {
                feedContainer.getChildren().remove(1, feedContainer.getChildren().size());
            }

            List<Map<String, Object>> eventos = objectMapper.readValue(jsonRespostaEventos,
                    new TypeReference<List<Map<String, Object>>>() {
                    });
            List<Map<String, Object>> publicacoes = objectMapper.readValue(jsonRespostaTextos,
                    new TypeReference<List<Map<String, Object>>>() {
                    });

            // Renderiza os Eventos
            for (Map<String, Object> ev : eventos) {
                String autorNome = (String) ev.get("nomeAutor");
                String autorCurso = (String) ev.get("cursoAutor");
                String titulo = (String) ev.get("titulo");
                String descricao = (String) ev.get("descricao");
                String dataEvento = ev.get("dataEvento") != null ? ev.get("dataEvento").toString() : "";
                String local = (String) ev.get("local");
                int curtidas = ev.get("curtidas") != null ? (int) ev.get("curtidas") : 0;
                Long idPub = ((Number) ev.get("id")).longValue();

                boolean canDelete = SessaoUsuario.usuarioLogadoNome != null
                        && SessaoUsuario.usuarioLogadoNome.equals(autorNome);

                EventoCardComponent[] cardHolder = new EventoCardComponent[1];
                cardHolder[0] = new EventoCardComponent(
                        autorNome, autorCurso, titulo, descricao, dataEvento, local, curtidas,
                        () -> curtirPublicacao(idPub),
                        () -> carregarComentariosEvento(idPub, cardHolder[0]),
                        (texto) -> enviarComentarioEvento(idPub, texto, cardHolder[0]),
                        canDelete,
                        () -> deletarEvento(idPub));
                feedContainer.getChildren().add(cardHolder[0]);
            }

            // Renderiza as Postagens de Texto
            for (Map<String, Object> pub : publicacoes) {
                String autorNome = (String) pub.get("nomeAutor");
                String autorCurso = (String) pub.get("cursoAutor");
                String conteudo = (String) pub.get("conteudo");
                String dataCriacao = pub.get("dataCriacao") != null ? pub.get("dataCriacao").toString() : "";
                int curtidas = pub.get("curtidas") != null ? (int) pub.get("curtidas") : 0;
                Long idPub = ((Number) pub.get("id")).longValue();

                boolean canDelete = SessaoUsuario.usuarioLogadoNome != null
                        && SessaoUsuario.usuarioLogadoNome.equals(autorNome);

                PostCardComponent[] cardHolder = new PostCardComponent[1];
                cardHolder[0] = new PostCardComponent(
                        autorNome, autorCurso, conteudo, dataCriacao, curtidas,
                        () -> curtirPublicacao(idPub),
                        () -> carregarComentariosPost(idPub, cardHolder[0]),
                        (texto) -> enviarComentarioPost(idPub, texto, cardHolder[0]),
                        canDelete,
                        () -> deletarPostagem(idPub));
                feedContainer.getChildren().add(cardHolder[0]);
            }
        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta("Erro ao carregar o feed", e.getMessage());
        }
    }

    private void carregarComentariosPost(Long idPublicacao, PostCardComponent card) {
        try {
            String json = interacaoClient.listarComentarios(idPublicacao);
            List<Map<String, Object>> comments = objectMapper.readValue(json,
                    new TypeReference<List<Map<String, Object>>>() {
                    });
            card.renderComments(comments,
                    idComentario -> curtirComentario(idComentario, () -> carregarComentariosPost(idPublicacao, card)));
        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta("Erro ao carregar comentários", e.getMessage());
        }
    }

    private void carregarComentariosEvento(Long idPublicacao, EventoCardComponent card) {
        try {
            String json = interacaoClient.listarComentarios(idPublicacao);
            List<Map<String, Object>> comments = objectMapper.readValue(json,
                    new TypeReference<List<Map<String, Object>>>() {
                    });
            card.renderComments(comments, idComentario -> curtirComentario(idComentario,
                    () -> carregarComentariosEvento(idPublicacao, card)));
        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta("Erro ao carregar comentários", e.getMessage());
        }
    }

    private void enviarComentarioPost(Long idPublicacao, String texto, PostCardComponent card) {
        if (SessaoUsuario.usuarioLogadoId == null) {
            mostrarAlerta("Erro", "Você não está logado.");
            return;
        }
        try {
            interacaoClient.adicionarComentario(idPublicacao, SessaoUsuario.usuarioLogadoId, texto);
            carregarComentariosPost(idPublicacao, card);
        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta("Erro ao enviar comentário", e.getMessage());
        }
    }

    private void enviarComentarioEvento(Long idPublicacao, String texto, EventoCardComponent card) {
        if (SessaoUsuario.usuarioLogadoId == null) {
            mostrarAlerta("Erro", "Você não está logado.");
            return;
        }
        try {
            interacaoClient.adicionarComentario(idPublicacao, SessaoUsuario.usuarioLogadoId, texto);
            carregarComentariosEvento(idPublicacao, card);
        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta("Erro ao enviar comentário", e.getMessage());
        }
    }

    private void curtirComentario(Long idComentario, Runnable reloadCallback) {
        try {
            interacaoClient.curtirComentario(idComentario);
            reloadCallback.run();
        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta("Erro ao curtir comentário", e.getMessage());
        }
    }

    @FXML
    public void postar() {
        String conteudo = campoNovaPostagem.getText();
        if (conteudo == null || conteudo.trim().isEmpty()) {
            mostrarAlerta("Aviso", "O conteúdo da postagem não pode ser vazio.");
            return;
        }

        if (SessaoUsuario.usuarioLogadoId == null) {
            mostrarAlerta("Erro", "Você não está logado.");
            return;
        }

        try {
            publicacaoClient.criarPostagem(SessaoUsuario.usuarioLogadoId, conteudo);
            campoNovaPostagem.clear();
            carregarFeed();
        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta("Erro ao postar", e.getMessage());
        }
    }

    @FXML
    public void criarEvento() {
        String titulo = campoEventoTitulo.getText();
        String descricao = campoEventoDescricao.getText();
        LocalDate data = campoEventoData.getValue();
        String local = campoEventoLocal.getText();

        if (titulo == null || titulo.trim().isEmpty() || descricao == null || descricao.trim().isEmpty() || data == null
                || local == null || local.trim().isEmpty()) {
            mostrarAlerta("Aviso", "Todos os campos do evento são obrigatórios.");
            return;
        }

        if (SessaoUsuario.usuarioLogadoId == null) {
            mostrarAlerta("Erro", "Você não está logado.");
            return;
        }

        try {
            publicacaoClient.criarEvento(SessaoUsuario.usuarioLogadoId, titulo, descricao, data.toString(), local);
            campoEventoTitulo.clear();
            campoEventoDescricao.clear();
            campoEventoData.setValue(null);
            campoEventoLocal.clear();
            carregarFeed();
        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta("Erro ao criar evento", e.getMessage());
        }
    }

    private void curtirPublicacao(Long idPublicacao) {
        try {
            publicacaoClient.curtirPublicacao(idPublicacao);
            carregarFeed();
        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta("Erro ao curtir", e.getMessage());
        }
    }

    private void deletarPostagem(Long id) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Excluir Postagem");
        alert.setHeaderText("Tem certeza que deseja excluir esta postagem?");
        alert.setContentText("Esta ação não pode ser desfeita.");
        if (alert.showAndWait().orElse(null) == javafx.scene.control.ButtonType.OK) {
            try {
                publicacaoClient.deletarPostagemTexto(id);
                carregarFeed();
            } catch (Exception e) {
                e.printStackTrace();
                mostrarAlerta("Erro", "Falha ao deletar postagem: " + e.getMessage());
            }
        }
    }

    private void deletarEvento(Long id) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Excluir Evento");
        alert.setHeaderText("Tem certeza que deseja excluir este evento?");
        alert.setContentText("Esta ação não pode ser desfeita.");
        if (alert.showAndWait().orElse(null) == javafx.scene.control.ButtonType.OK) {
            try {
                publicacaoClient.deletarEvento(id);
                carregarFeed();
            } catch (Exception e) {
                e.printStackTrace();
                mostrarAlerta("Erro", "Falha ao deletar evento: " + e.getMessage());
            }
        }
    }

    @FXML
    public void irParaPerfil() {
        IntegraUfgApp.mudarTela("perfil.fxml");
    }

    @FXML
    public void fazerLogout() {
        SessaoUsuario.limparSessao();
        IntegraUfgApp.mudarTela("Login.fxml");
    }

    private void mostrarAlerta(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}
