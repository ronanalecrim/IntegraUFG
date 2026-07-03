package ufg.IntegraUFG.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class IntegraUfgApp extends Application {

    private static Stage palcoPrincipal;

    @Override
    public void start(Stage primaryStage) throws Exception {
        palcoPrincipal = primaryStage;
        palcoPrincipal.setTitle("IntegraUFG - Plataforma Acadêmica");

        // Quando o app abre, carrega a tela de login primeiro
        mudarTela("login.fxml");

        palcoPrincipal.show();
    }

    // Método que pode ser chamado de qualquer Controller para trocar de tela
    public static void mudarTela(String arquivoFxml) {
        try {
            Parent raiz = FXMLLoader.load(IntegraUfgApp.class.getResource("/" + arquivoFxml));
            Scene cena = new Scene(raiz, 800, 600);
            palcoPrincipal.setScene(cena);
        } catch (Exception e) {
            System.out.println("Erro ao carregar a tela " + arquivoFxml + ": " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}