package bse202.sda.jigsaw.controllers;

import bse202.sda.jigsaw.models.fxml.Field;
import bse202.sda.jigsaw.models.fxml.Figure;
import bse202.sda.jigsaw.utils.Dragger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ResourceBundle;

public class GameController implements Initializable {

    @FXML
    public Field field;

    @FXML
    public Pane pane;

    @FXML
    public Pane mainBox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        field.draggedNode = Dragger::getLastDragged;
        Figure f1 = new Figure(Figure.FigureType.FIRST, 50, Color.GOLD);
        Figure f2 = new Figure(Figure.FigureType.SECOND, 50, Color.GOLD);
        Figure f3 = new Figure(Figure.FigureType.THIRD, 50, Color.GOLD);

        Dragger dg1 = new Dragger(f1);
        Dragger dg2 = new Dragger(f2);
        Dragger dg3 = new Dragger(f3);

        mainBox.getChildren().add(f1);
        mainBox.getChildren().add(f2);
        mainBox.getChildren().add(f3);

    }
}