package bse202.sda.jigsaw.controllers;

import bse202.sda.jigsaw.models.fxml.Cell;
import bse202.sda.jigsaw.models.fxml.Field;
import bse202.sda.jigsaw.models.fxml.Figure;
import bse202.sda.jigsaw.utils.Dragger;
import bse202.sda.jigsaw.utils.Spawner;
import bse202.sda.jigsaw.utils.Timer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class GameController implements Initializable {
    @FXML
    public Field field;
    @FXML
    public Pane pane;
    @FXML
    public VBox mainBox;
    @FXML
    public HBox figuresSpawn;

    @FXML
    public ImageView minutes1;
    @FXML
    public ImageView minutes2;
    @FXML
    public ImageView seconds1;
    @FXML
    public ImageView seconds2;

    @FXML
    public Button stopButton;

    Timer timer;
    Spawner spawner;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        field.draggedNode = Dragger::getLastDragged;
        List<Node> figures = new ArrayList<>();


        for (Figure.FigureType type :
                Figure.FigureType.values()) {
            figures.add(new Figure(type, 60, Color.GOLD));
            Dragger dg = new Dragger(figures.get(figures.size() - 1));
        }

        spawner = new Spawner(figuresSpawn, figures);
        List<List<Cell>> grid = field.getGrid();
        for (List<Cell> cells : grid) {
            for (int j = 0; j < grid.get(0).size(); j++) {
                cells.get(j).onFigurePlaced = spawner::respawn;
            }
        }

        this.minutes1.setFitHeight(80);
        this.minutes1.setFitWidth(80);
        this.minutes2.setFitHeight(80);
        this.minutes2.setFitWidth(80);
        this.seconds1.setFitHeight(80);
        this.seconds1.setFitWidth(80);
        this.seconds2.setFitHeight(80);
        this.seconds2.setFitWidth(80);

        timer = new Timer(0,
                minutes1, minutes2, seconds1, seconds2);
        timer.start();
    }

    public void endGame(ActionEvent e) {
        timer.stop();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Game info");
        alert.setHeaderText(String.format(
                "Total time spent: %d minutes, %d seconds\n\r" +
                        "Total figures placed: %d",
                timer.getMinutes(), timer.getSeconds(),
                spawner.getTotalSpawns() - 1));
        alert.showAndWait().ifPresent(rs -> {
        });

        Stage stage = (Stage) (stopButton.getScene().getWindow());
        stage.close();
    }
}