package bse202.sda.jigsaw;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class JigsawGame extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(
                JigsawGame.class.getResource("views/game.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1240, 980);
        scene.getStylesheets().add(Objects.requireNonNull(JigsawGame.class
                .getResource("views/css/styles.css")).toExternalForm());
        stage.setTitle("Jigsaw By DmitriySH");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}