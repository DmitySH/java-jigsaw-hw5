package bse202.sda.jigsaw;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

public class JigsawGame extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(
                JigsawGame.class.getResource("views/main.fxml"));

        Scene mainScene = new Scene(fxmlLoader.load(), 340, 580);
        mainScene.getStylesheets().add(Objects.requireNonNull(JigsawGame.class
                .getResource("views/css/styles.css")).toExternalForm());

        stage.setTitle("Jigsaw by DmittySH");
        stage.setScene(mainScene);
        stage.show();
        stage.setMinHeight(stage.getHeight());
        stage.setMaxHeight(stage.getHeight());
        stage.setMinWidth(stage.getWidth());
        stage.setMaxWidth(stage.getWidth());

        EventHandler<WindowEvent> confirmCloseEventHandler = event -> {
            Alert closeConfirmation = new Alert(
                    Alert.AlertType.CONFIRMATION,
                    "Are you sure you want to exit?"
            );
            Button exitButton = (Button) closeConfirmation.getDialogPane().lookupButton(
                    ButtonType.OK
            );
            exitButton.setText("Exit");
            closeConfirmation.setHeaderText("Confirm Exit");

            Optional<ButtonType> closeResponse = closeConfirmation.showAndWait();
            if (!ButtonType.OK.equals(closeResponse.orElse(ButtonType.OK))) {
                event.consume();
            }
        };

        stage.setOnCloseRequest(confirmCloseEventHandler);
    }

    public static void main(String[] args) {
        launch();
    }
}