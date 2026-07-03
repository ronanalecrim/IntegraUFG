package ufg.IntegraUFG.view;

import ufg.IntegraUFG.client.UsuarioClient;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;

public class TelaCadastroController {

    @FXML
    private TextField campoNome;
    @FXML
    private TextField campoEmail;
    @FXML
    private TextField campoCurso;
    @FXML
    private PasswordField campoSenha;

    private final UsuarioClient usuarioClient = new UsuarioClient();

    @FXML
    public void fazerCadastro() {
        try {
            String nome = campoNome.getText();
            String email = campoEmail.getText();
            String curso = campoCurso.getText();
            String senha = campoSenha.getText();

            String resposta = usuarioClient.cadastrarUsuario(nome, email, senha, curso);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Sucesso!");
            alert.setHeaderText("Cadastro realizado");
            alert.setContentText("Sua conta foi criada. Você já pode fazer login.");
            alert.showAndWait();

            // Volta para a tela de login automaticamente após o sucesso
            IntegraUfgApp.mudarTela("login.fxml");

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro no Cadastro");
            alert.setHeaderText("Não foi possível criar a conta.");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    // Método para o botão Voltar
    @FXML
    public void voltarParaLogin() {
        IntegraUfgApp.mudarTela("login.fxml");
    }
}