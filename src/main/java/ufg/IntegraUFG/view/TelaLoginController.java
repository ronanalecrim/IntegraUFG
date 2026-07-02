package ufg.IntegraUFG.view;

import ufg.IntegraUFG.client.UsuarioClient;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;

public class TelaLoginController {

    // Esses nomes devem ser os mesmos fx:id que estão no seu arquivo .fxml
    @FXML private TextField campoEmail;
    @FXML private PasswordField campoSenha;

    private final UsuarioClient usuarioClient = new UsuarioClient();

    // Esse é o método que você vincula ao botão "Entrar" no Scene Builder ou no FXML (onAction="#fazerLogin")
    @FXML
    public void fazerLogin() {
        try {
            String email = campoEmail.getText();
            String senha = campoSenha.getText();

            // Aqui a mágica acontece: a tela chama o Client, que chama a API
            String respostaJson = usuarioClient.fazerLogin(email, senha);

            System.out.println("Deu certo! Usuário logado: " + respostaJson);

            // Aqui você colocaria o código para fechar a tela de login e abrir a tela do Feed

        } catch (Exception e) {
            // Se a API retornar erro (ex: senha errada), mostramos um popup na tela
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro no Login");
            alert.setHeaderText("Não foi possível acessar a conta.");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
}