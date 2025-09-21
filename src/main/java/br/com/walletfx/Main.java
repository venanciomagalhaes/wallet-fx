package br.com.walletfx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("views/main.fxml"));

        Scene scene = new Scene(fxmlLoader.load());

        stage.setTitle("WalletFx");

        Image icon = new Image(Objects.requireNonNull(
                getClass().getResourceAsStream("/br/com/walletfx/icons/cifrao.png")
        ));
        stage.getIcons().add(icon);
        stage.setMinWidth(1400);
        stage.setMinHeight(600);
        stage.setScene(scene);
        stage.show();
    }
}
