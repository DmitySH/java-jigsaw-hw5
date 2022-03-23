package bse202.sda.jigsaw.controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class GameController implements Initializable {


    @FXML
    private Label label;
    @FXML
    private TextField textfield;

    @FXML
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        label.setText("Hello  " + textfield.getText());
    }

    @FXML
    public GridPane field;
    @FXML
    public VBox vb;

    private void createGrass() {
        // turn layout pixel snapping off on the grid so that grid lines will be an even width.
        field.setSnapToPixel(false);
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                Rectangle box = new Rectangle(50, 50, Color.GRAY);
//                box.setStrokeType(StrokeType.INSIDE);
//                box.setStyle("-fx-stroke-width: 10, 1, 1, 1");
////                box.setStrokeWidth(5, 5, 5, 5);
//                box.setStroke(Color.GREEN);
                Line line = new Line();
                field.add(box, i, j);
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        createGrass();

    }
}