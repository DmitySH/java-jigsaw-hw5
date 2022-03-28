package bse202.sda.jigsaw.controllers;

import bse202.sda.jigsaw.JigsawGame;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    @FXML
    public Button startButton;
    @FXML
    public Button quitButton;
    @FXML
    public Button authorButton;
    @FXML
    public ImageView logo;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        InputStream input = JigsawGame.class
                .getResourceAsStream("views/images/logo.png");
        Image image = new Image(Objects.requireNonNull(input));
        logo.setFitWidth(160);
        logo.setFitHeight(160);
        logo.setImage(image);
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
            newWindow.setTitle("Jigsaw by DmittySH");
            newWindow.setScene(gameScene);
            newWindow.show();
        } catch (IOException ex) {
            System.out.println("Unable to create game window");
        }
    }

    public void closeApp(ActionEvent e) {
        Stage stage = (Stage) (quitButton.getScene().getWindow());
        stage.fireEvent(
                new WindowEvent(
                        stage,
                        WindowEvent.WINDOW_CLOSE_REQUEST
                )
        );
    }

    public void showAuthor(ActionEvent e) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Author");
        alert.setHeaderText("Game was designed and created  by Dmitty\nBSE202");
        alert.showAndWait();
    }
}
