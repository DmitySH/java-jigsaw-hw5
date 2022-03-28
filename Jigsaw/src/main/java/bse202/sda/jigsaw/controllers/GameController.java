package bse202.sda.jigsaw.controllers;

import bse202.sda.jigsaw.models.fxml.Cell;
import bse202.sda.jigsaw.models.fxml.Field;
import bse202.sda.jigsaw.models.fxml.Figure;
import bse202.sda.jigsaw.utils.Dragger;
import bse202.sda.jigsaw.utils.Spawner;
import bse202.sda.jigsaw.utils.Timer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        field.draggedNode = Dragger::getLastDragged;
        List<Node> figures = new ArrayList<>();


        for (Figure.FigureType type :
                Figure.FigureType.values()) {
            figures.add(new Figure(type, 60, Color.GOLD));
            Dragger dg = new Dragger(figures.get(figures.size() - 1));
        }


        Spawner spawner = new Spawner(figuresSpawn, figures);

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

        Timer timer = new Timer(0,
                minutes1, minutes2, seconds1, seconds2);
        timer.start();
    }
}