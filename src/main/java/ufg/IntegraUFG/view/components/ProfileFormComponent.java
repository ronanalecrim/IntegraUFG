package ufg.IntegraUFG.view.components;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.util.function.Consumer;

public class ProfileFormComponent extends VBox {

    private final TextField campoNome = new TextField();
    private final TextField campoCurso = new TextField();
    private final PasswordField campoSenha = new PasswordField();

    public ProfileFormComponent(String nomeAtual, String cursoAtual, Consumer<String[]> onUpdateClicked, Runnable onDeleteClicked) {
        this.getStyleClass().add("card");
        this.setSpacing(15);
        this.setPadding(new Insets(30));
        this.setStyle("-fx-max-width: 450px; -fx-alignment: center-left; -fx-background-color: white;");
        
        Label titulo = new Label("Meu Perfil");
        titulo.setStyle("-fx-font-weight: bold; -fx-font-size: 24px; -fx-text-fill: #2C3E50; -fx-padding: 0 0 10 0;");
        
        VBox nomeBox = new VBox(5);
        Label lblNome = new Label("Nome:");
        lblNome.setStyle("-fx-text-fill: #7F8C8D; -fx-font-weight: bold;");
        campoNome.setText(nomeAtual != null ? nomeAtual : "");
        campoNome.setPromptText("Seu Nome");
        campoNome.getStyleClass().add("text-field");
        nomeBox.getChildren().addAll(lblNome, campoNome);
        
        VBox cursoBox = new VBox(5);
        Label lblCurso = new Label("Curso:");
        lblCurso.setStyle("-fx-text-fill: #7F8C8D; -fx-font-weight: bold;");
        campoCurso.setText(cursoAtual != null ? cursoAtual : "");
        campoCurso.setPromptText("Seu Curso");
        campoCurso.getStyleClass().add("text-field");
        cursoBox.getChildren().addAll(lblCurso, campoCurso);
        
        VBox senhaBox = new VBox(5);
        Label lblSenha = new Label("Nova Senha:");
        lblSenha.setStyle("-fx-text-fill: #7F8C8D; -fx-font-weight: bold;");
        campoSenha.setPromptText("Deixe em branco para não alterar");
        campoSenha.getStyleClass().add("text-field");
        senhaBox.getChildren().addAll(lblSenha, campoSenha);
        
        VBox btnBox = new VBox(10);
        btnBox.setPadding(new Insets(20, 0, 0, 0));
        
        Button btnSalvar = new Button("Atualizar Dados");
        btnSalvar.getStyleClass().add("primary-button");
        btnSalvar.setMaxWidth(Double.MAX_VALUE);
        btnSalvar.setOnAction(e -> {
            if(onUpdateClicked != null) {
                onUpdateClicked.accept(new String[]{campoNome.getText(), campoCurso.getText(), campoSenha.getText()});
            }
        });
        
        Button btnExcluir = new Button("Excluir Conta Permanentemente");
        btnExcluir.setStyle("-fx-background-color: transparent; -fx-border-color: #E74C3C; -fx-text-fill: #E74C3C; -fx-font-weight: bold; -fx-padding: 10 20; -fx-border-radius: 5px; -fx-cursor: hand;");
        btnExcluir.setMaxWidth(Double.MAX_VALUE);
        btnExcluir.setOnAction(e -> {
            if(onDeleteClicked != null) {
                onDeleteClicked.run();
            }
        });
        
        btnBox.getChildren().addAll(btnSalvar, btnExcluir);

        this.getChildren().addAll(titulo, nomeBox, cursoBox, senhaBox, btnBox);
    }
}
