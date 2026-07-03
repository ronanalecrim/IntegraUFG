package ufg.IntegraUFG.view.components;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class EventoCardComponent extends VBox {

    public EventoCardComponent(String autorNome, String autorCurso, String titulo, String descricao, String dataEvento, String local, int curtidas, Runnable onLikeClicked) {
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
        
        HBox footer = new HBox(btnLike, lblCurtidas);
        footer.setSpacing(10);
        footer.setStyle("-fx-alignment: center-left; -fx-padding: 10 0 0 0;");
        
        this.getChildren().addAll(header, lblTitulo, lblDescricao, lblDataLocal, footer);
    }
}
