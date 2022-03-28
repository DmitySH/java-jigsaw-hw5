package bse202.sda.jigsaw.controllers;

import bse202.sda.jigsaw.JigsawGame;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    @FXML
    public Button startButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void createGameWindow(ActionEvent e) {
        FXMLLoader fxmlLoader = new FXMLLoader(
                JigsawGame.class.getResource("views/game.fxml"));
        Stage newWindow = new Stage();

        try {
            Scene gameScene = new Scene(fxmlLoader.load(), 780, 980);
            gameScene.getStylesheets().add(Objects.requireNonNull(JigsawGame.class
                    .getResource("views/css/styles.css")).toExternalForm());

            newWindow.setMinHeight(1015);
            newWindow.setMinWidth(730);

            newWindow.initModality(Modality.WINDOW_MODAL);
            newWindow.initOwner(startButton.getScene().getWindow());
            newWindow.setTitle("Jigsaw By DmitriySH");
            newWindow.setScene(gameScene);
            newWindow.show();
        } catch (IOException ex) {
            System.out.println("Unable to create game window");
        }
    }
}
