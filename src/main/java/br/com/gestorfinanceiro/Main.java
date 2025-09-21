package br.com.gestorfinanceiro;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("views/main.fxml"));

        Scene scene = new Scene(fxmlLoader.load());

        stage.setTitle("Gestor financeiro");
        stage.setMinWidth(1400);
        stage.setMinHeight(600);
        stage.setScene(scene);
        stage.show();
    }
}
