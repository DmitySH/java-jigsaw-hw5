package bse202.sda.jigsaw.controllers;

import bse202.sda.jigsaw.models.fxml.Cell;
import bse202.sda.jigsaw.models.fxml.Field;
import bse202.sda.jigsaw.models.fxml.Figure;
import bse202.sda.jigsaw.recources.GameColors;
import bse202.sda.jigsaw.utils.Dragger;
import bse202.sda.jigsaw.utils.Spawner;
import bse202.sda.jigsaw.utils.Timer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
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
    public HBox mainBox;
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
            figures.add(new Figure(type, 50, GameColors.getInstance().SmoothBlue()));
            Dragger dg = new Dragger(figures.get(figures.size() - 1));
            dg.setDraggableProperty(true);
        }

        spawner = new Spawner(figuresSpawn, figures);
        List<List<Cell>> grid = field.getGrid();
        for (List<Cell> cells : grid) {
            for (int j = 0; j < grid.get(0).size(); j++) {
                cells.get(j).onFigurePlaced = spawner::respawn;
            }
        }

        startTimer();
    }

    /**
     * Starts timer.
     */
    private void startTimer() {
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

    /**
     * Stops current game and shows stat.
     *
     * @param e event.
     */
    public void endGame(ActionEvent e) {
        timer.stop();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Game info");
        alert.setHeaderText(String.format(
                """
                        Total time spent: %d minutes, %d seconds
                        Total figures placed: %d
                        Cells covered: %d / %d
                        """,
                timer.getMinutes(), timer.getSeconds(),
                spawner.getTotalSpawns() - 1, calculateNumberOfFilled(),
                field.getRows() * field.getColumns()
        ));
        alert.showAndWait();

        Stage stage = (Stage) (stopButton.getScene().getWindow());
        stage.close();
    }

    /**
     * Finds number of filled cells in a field.
     *
     * @return number of filled cells.
     */
    private int calculateNumberOfFilled() {
        int sum = 0;
        for (int i = 0; i < field.getColumns(); i++) {
            for (int j = 0; j < field.getRows(); j++) {
                if (field.getGrid().get(i).get(j).IsFilled()) {
                    ++sum;
                }
            }
        }

        return sum;
    }
}