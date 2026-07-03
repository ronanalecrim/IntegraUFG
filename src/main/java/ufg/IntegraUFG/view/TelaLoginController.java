package ufg.IntegraUFG.view;

import ufg.IntegraUFG.client.UsuarioClient;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;

public class TelaLoginController {

    @FXML
    private TextField campoEmail;
    @FXML
    private PasswordField campoSenha;

    private final UsuarioClient usuarioClient = new UsuarioClient();

    @FXML
    public void fazerLogin() {
        try {
            String email = campoEmail.getText();
            String senha = campoSenha.getText();

            String respostaJson = usuarioClient.fazerLogin(email, senha);

            System.out.println("Deu certo! Usuário logado: " + respostaJson);

            ObjectMapper mapper = new ObjectMapper();
            JsonNode node = mapper.readTree(respostaJson);
            SessaoUsuario.usuarioLogadoId = node.get("id").asLong();
            SessaoUsuario.usuarioLogadoNome = node.get("nome").asText();
            if (node.has("curso") && !node.get("curso").isNull()) {
                SessaoUsuario.usuarioLogadoCurso = node.get("curso").asText();
            }

            // Redireciona para o Feed
            IntegraUfgApp.mudarTela("feed.fxml");

        } catch (Exception e) {
            // Se a API retornar erro (ex: senha errada), mostramos um popup na tela
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro no Login");
            alert.setHeaderText("Não foi possível acessar a conta.");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    public void irParaCadastro() {
        IntegraUfgApp.mudarTela("cadastro.fxml");
    }
}