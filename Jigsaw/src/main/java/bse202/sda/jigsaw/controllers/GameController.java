package bse202.sda.jigsaw.controllers;

import bse202.sda.jigsaw.models.fxml.Field;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class GameController implements Initializable {

    @FXML
    public Field field;

    @FXML
    public VBox vb;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}