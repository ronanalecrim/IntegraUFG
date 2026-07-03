package ufg.IntegraUFG.view.components;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class PostCardComponent extends VBox {

    public PostCardComponent(String autorNome, String autorCurso, String conteudo, String dataCriacao, int curtidas, Runnable onLikeClicked) {
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
        
        // Conteúdo
        Label lblConteudo = new Label(conteudo);
        lblConteudo.setWrapText(true);
        lblConteudo.setStyle("-fx-font-size: 14px; -fx-text-fill: #34495E;");
        
        // Footer (Data e Curtidas)
        Label lblData = new Label(dataCriacao != null ? dataCriacao : "");
        lblData.setStyle("-fx-text-fill: #BDC3C7; -fx-font-size: 12px;");
        
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
        
        HBox footerLeft = new HBox(btnLike, lblCurtidas);
        footerLeft.setSpacing(10);
        footerLeft.setStyle("-fx-alignment: center-left;");
        
        HBox footer = new HBox(footerLeft, lblData);
        footer.setSpacing(15);
        footer.setStyle("-fx-alignment: center-left; -fx-padding: 10 0 0 0;");
        
        // Espaçador para empurrar a data para a direita (opcional, por simplicidade usamos HBox normal)
        
        this.getChildren().addAll(header, lblConteudo, footer);
    }
}
