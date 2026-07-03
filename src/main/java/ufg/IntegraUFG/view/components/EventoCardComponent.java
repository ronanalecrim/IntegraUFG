package ufg.IntegraUFG.view.components;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class EventoCardComponent extends VBox {

    private final VBox commentsContainer = new VBox();
    private final VBox commentsSection = new VBox();
    private boolean commentsVisible = false;

    public EventoCardComponent(String autorNome, String autorCurso, String titulo, String descricao, String dataEvento, String local, int curtidas, Runnable onLikeClicked, Runnable onToggleComments, Consumer<String> onSubmitComment, boolean canDelete, Runnable onDeleteClicked) {
        this.getStyleClass().add("card");
        this.setSpacing(10);
        this.setPadding(new Insets(15));
        
        // Header (Autor e Curso)
        Label lblNome = new Label(autorNome);
        lblNome.setStyle("-fx-font-weight: bold; -fx-font-size: 14px; -fx-text-fill: #2C3E50;");
        Label lblCurso = new Label(autorCurso != null && !autorCurso.isEmpty() ? " • " + autorCurso : "");
        lblCurso.setStyle("-fx-text-fill: #7F8C8D; -fx-font-size: 12px;");
        
        HBox header = new HBox(lblNome, lblCurso);
        header.setSpacing(5);
        header.setStyle("-fx-alignment: center-left;");
        
        // Evento Detalhes
        Label lblTitulo = new Label(titulo);
        lblTitulo.setStyle("-fx-font-weight: bold; -fx-font-size: 16px; -fx-text-fill: #2980B9;");
        
        Label lblDescricao = new Label(descricao);
        lblDescricao.setWrapText(true);
        lblDescricao.setStyle("-fx-font-size: 14px; -fx-text-fill: #34495E;");
        
        Label lblDataLocal = new Label("📅 " + (dataEvento != null ? dataEvento : "") + "  📍 " + (local != null ? local : ""));
        lblDataLocal.setStyle("-fx-font-size: 13px; -fx-text-fill: #E67E22; -fx-font-weight: bold;");
        
        // Footer (Curtidas)
        Label lblCurtidas = new Label(curtidas + " curtidas");
        lblCurtidas.setStyle("-fx-text-fill: #7F8C8D; -fx-font-size: 12px;");
        
        Button btnLike = new Button("Curtir");
        btnLike.getStyleClass().add("secondary-button");
        btnLike.setStyle("-fx-border-color: #3498DB; -fx-text-fill: #3498DB; -fx-padding: 5 10; -fx-font-size: 12px;");
        btnLike.setOnAction(e -> {
            if(onLikeClicked != null) {
                onLikeClicked.run();
            }
        });

        Button btnVerComentarios = new Button("Comentários");
        btnVerComentarios.setStyle("-fx-background-color: transparent; -fx-text-fill: #34495E; -fx-underline: true; -fx-cursor: hand;");
        btnVerComentarios.setOnAction(e -> {
            commentsVisible = !commentsVisible;
            commentsSection.setVisible(commentsVisible);
            commentsSection.setManaged(commentsVisible);
            if (commentsVisible && onToggleComments != null) {
                onToggleComments.run();
            }
        });
        
        HBox footerLeft = new HBox(btnLike, lblCurtidas, btnVerComentarios);
        if (canDelete) {
            Button btnDelete = new Button("Deletar");
            btnDelete.setStyle("-fx-background-color: transparent; -fx-text-fill: #E74C3C; -fx-underline: true; -fx-cursor: hand;");
            btnDelete.setOnAction(e -> {
                if(onDeleteClicked != null) onDeleteClicked.run();
            });
            footerLeft.getChildren().add(btnDelete);
        }
        footerLeft.setSpacing(10);
        footerLeft.setStyle("-fx-alignment: center-left; -fx-padding: 10 0 0 0;");

        // Seção de Comentários
        commentsSection.setSpacing(10);
        commentsSection.setPadding(new Insets(10, 0, 0, 15));
        commentsSection.setStyle("-fx-border-color: #ECF0F1; -fx-border-width: 1 0 0 0; -fx-margin-top: 10px;");
        commentsSection.setVisible(false);
        commentsSection.setManaged(false);

        commentsContainer.setSpacing(8);

        HBox addCommentBox = new HBox();
        addCommentBox.setSpacing(10);
        TextField commentField = new TextField();
        commentField.setPromptText("Escreva um comentário...");
        commentField.setStyle("-fx-pref-width: 300px; -fx-padding: 5px;");
        Button btnSubmitComment = new Button("Enviar");
        btnSubmitComment.setStyle("-fx-background-color: #34495E; -fx-text-fill: white;");
        btnSubmitComment.setOnAction(e -> {
            String text = commentField.getText();
            if(text != null && !text.trim().isEmpty() && onSubmitComment != null) {
                onSubmitComment.accept(text);
                commentField.clear();
            }
        });
        addCommentBox.getChildren().addAll(commentField, btnSubmitComment);

        commentsSection.getChildren().addAll(commentsContainer, addCommentBox);

        this.getChildren().addAll(header, lblTitulo, lblDescricao, lblDataLocal, footerLeft, commentsSection);
    }

    public void renderComments(List<Map<String, Object>> comments, Consumer<Long> onLikeComment) {
        commentsContainer.getChildren().clear();
        if (comments == null || comments.isEmpty()) {
            Label noComments = new Label("Nenhum comentário ainda.");
            noComments.setStyle("-fx-text-fill: #BDC3C7; -fx-font-style: italic;");
            commentsContainer.getChildren().add(noComments);
            return;
        }

        for (Map<String, Object> c : comments) {
            VBox commentBox = new VBox();
            commentBox.setSpacing(3);
            commentBox.setStyle("-fx-background-color: #F8F9F9; -fx-padding: 8px; -fx-background-radius: 5px;");
            
            String author = (String) c.get("nomeAutor");
            String text = (String) c.get("texto");
            int curtidas = c.get("curtidas") != null ? (int) c.get("curtidas") : 0;
            Long id = ((Number) c.get("id")).longValue();

            Label lblAuthor = new Label(author);
            lblAuthor.setStyle("-fx-font-weight: bold; -fx-font-size: 12px;");
            Label lblText = new Label(text);
            lblText.setStyle("-fx-font-size: 12px;");
            lblText.setWrapText(true);

            HBox commentFooter = new HBox();
            commentFooter.setSpacing(10);
            Button btnLike = new Button("Curtir (" + curtidas + ")");
            btnLike.setStyle("-fx-background-color: transparent; -fx-text-fill: #3498DB; -fx-font-size: 10px; -fx-cursor: hand;");
            btnLike.setOnAction(e -> {
                if(onLikeComment != null) onLikeComment.accept(id);
            });
            commentFooter.getChildren().add(btnLike);

            commentBox.getChildren().addAll(lblAuthor, lblText, commentFooter);
            commentsContainer.getChildren().add(commentBox);
        }
    }
}
