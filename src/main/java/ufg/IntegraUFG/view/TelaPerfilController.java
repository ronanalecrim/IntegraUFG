package ufg.IntegraUFG.view;

import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.VBox;
import ufg.IntegraUFG.client.UsuarioClient;
import ufg.IntegraUFG.view.components.ProfileFormComponent;

import java.util.Map;
import java.util.Optional;

public class TelaPerfilController {

    @FXML private VBox perfilContainer;

    private final UsuarioClient usuarioClient = new UsuarioClient();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @FXML
    public void initialize() {
        carregarPerfil();
    }

    private void carregarPerfil() {
        if (SessaoUsuario.usuarioLogadoId == null) {
            IntegraUfgApp.mudarTela("Login.fxml");
            return;
        }

        try {
            String jsonResposta = usuarioClient.buscarPerfil(SessaoUsuario.usuarioLogadoId);
            Map<String, Object> usuario = objectMapper.readValue(jsonResposta, Map.class);
            
            String nome = (String) usuario.get("nome");
            String curso = (String) usuario.get("curso");

            ProfileFormComponent profileForm = new ProfileFormComponent(nome, curso, 
                dados -> atualizarPerfil(dados[0], dados[1], dados[2]), 
                this::excluirConta
            );

            perfilContainer.getChildren().clear();
            perfilContainer.getChildren().add(profileForm);

        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta("Erro", "Não foi possível carregar o perfil: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void atualizarPerfil(String nome, String curso, String senha) {
        if (nome == null || nome.trim().isEmpty() || curso == null || curso.trim().isEmpty()) {
            mostrarAlerta("Aviso", "Nome e Curso são obrigatórios.", Alert.AlertType.WARNING);
            return;
        }

        try {
            String jsonResposta = usuarioClient.atualizarPerfil(SessaoUsuario.usuarioLogadoId, nome, curso, senha);
            Map<String, Object> usuarioAtualizado = objectMapper.readValue(jsonResposta, Map.class);
            
            // Atualiza sessão local
            SessaoUsuario.usuarioLogadoNome = (String) usuarioAtualizado.get("nome");
            SessaoUsuario.usuarioLogadoCurso = (String) usuarioAtualizado.get("curso");
            
            mostrarAlerta("Sucesso", "Perfil atualizado com sucesso!", Alert.AlertType.INFORMATION);
            carregarPerfil(); // Recarrega os campos com as novas infos
        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta("Erro", "Não foi possível atualizar o perfil: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void excluirConta() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmação de Exclusão");
        alert.setHeaderText("Tem certeza que deseja excluir sua conta?");
        alert.setContentText("Esta ação é irreversível. Você perderá acesso permanentemente.");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                usuarioClient.excluirConta(SessaoUsuario.usuarioLogadoId);
                SessaoUsuario.limparSessao();
                mostrarAlerta("Conta Excluída", "Sua conta foi removida com sucesso.", Alert.AlertType.INFORMATION);
                IntegraUfgApp.mudarTela("Login.fxml");
            } catch (Exception e) {
                e.printStackTrace();
                mostrarAlerta("Erro", "Não foi possível excluir a conta: " + e.getMessage(), Alert.AlertType.ERROR);
            }
        }
    }

    @FXML
    public void voltarParaFeed() {
        IntegraUfgApp.mudarTela("feed.fxml");
    }

    private void mostrarAlerta(String titulo, String mensagem, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}
