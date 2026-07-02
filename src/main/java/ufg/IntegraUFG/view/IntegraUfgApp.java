package ufg.IntegraUFG.view;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class IntegraUfgApp extends Application {

    private Stage primaryStage;
    private Scene loginScene;
    private Scene feedScene;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("IntegraUFG - Plataforma Acadêmica");

        criarTelaLogin();
        criarTelaFeed();

        primaryStage.setScene(loginScene);
        primaryStage.show();
    }

    private void criarTelaLogin() {
        VBox loginLayout = new VBox(15);
        loginLayout.setAlignment(Pos.CENTER);
        loginLayout.setPadding(new Insets(40));
        loginLayout.getStyleClass().add("root-layout");

        Label titulo = new Label("IntegraUFG");
        titulo.getStyleClass().add("title-label");

        Label subtitulo = new Label("Faça login para continuar");
        subtitulo.getStyleClass().add("subtitle-label");

        // Representa os dados do LoginRequestDTO.java
        TextField campoEmail = new TextField();
        campoEmail.setPromptText("E-mail institucional");
        campoEmail.setMaxWidth(300);

        PasswordField campoSenha = new PasswordField();
        campoSenha.setPromptText("Senha");
        campoSenha.setMaxWidth(300);

        Button btnLogin = new Button("Entrar");
        btnLogin.setMaxWidth(300);
        btnLogin.getStyleClass().add("primary-button");
        btnLogin.setOnAction(e -> {
            // Aqui você chamaria o UsuarioController via HTTP/REST
            primaryStage.setScene(feedScene);
        });

        loginLayout.getChildren().addAll(titulo, subtitulo, campoEmail, campoSenha, btnLogin);
        loginScene = new Scene(loginLayout, 800, 600);
        loginScene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
    }

    private void criarTelaFeed() {
        BorderPane feedLayout = new BorderPane();
        feedLayout.getStyleClass().add("root-layout");

        // Topbar
        HBox topBar = new HBox(20);
        topBar.setPadding(new Insets(15, 20, 15, 20));
        topBar.getStyleClass().add("top-bar");
        Label logo = new Label("IntegraUFG Feed");
        logo.getStyleClass().add("logo-label");
        
        Button btnSair = new Button("Sair");
        btnSair.getStyleClass().add("secondary-button");
        btnSair.setOnAction(e -> primaryStage.setScene(loginScene));
        
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        topBar.getChildren().addAll(logo, spacer, btnSair);

        // Representa a criação de uma Publicacao.java / PostagemTexto.java
        VBox feedContent = new VBox(20);
        feedContent.setPadding(new Insets(20));
        feedContent.setAlignment(Pos.TOP_CENTER);

        VBox criarPost = new VBox(10);
        criarPost.getStyleClass().add("card");
        criarPost.setMaxWidth(600);
        
        TextArea campoPost = new TextArea();
        campoPost.setPromptText("O que você deseja compartilhar com a comunidade acadêmica?");
        campoPost.setPrefRowCount(3);
        
        Button btnPublicar = new Button("Publicar Texto");
        btnPublicar.getStyleClass().add("primary-button");

        criarPost.getChildren().addAll(campoPost, btnPublicar);

        // Exemplo de Postagem renderizada
        VBox postExemplo = new VBox(10);
        postExemplo.getStyleClass().add("card");
        postExemplo.setMaxWidth(600);
        Label autor = new Label("Professor João (Engenharia de Software)");
        autor.setStyle("-fx-font-weight: bold; -fx-text-fill: #333;");
        Label conteudoPost = new Label("Lembrete: A entrega do documento de requisitos foi adiada para sexta-feira!");
        conteudoPost.setWrapText(true);
        postExemplo.getChildren().addAll(autor, conteudoPost);

        feedContent.getChildren().addAll(criarPost, postExemplo);

        ScrollPane scrollPane = new ScrollPane(feedContent);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: transparent;");

        feedLayout.setTop(topBar);
        feedLayout.setCenter(scrollPane);

        feedScene = new Scene(feedLayout, 800, 600);
        feedScene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
    }

    public static void main(String[] args) {
        launch(args);
    }
}