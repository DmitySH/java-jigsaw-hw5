package bse202.sda.jigsaw.utils;

import bse202.sda.jigsaw.models.fxml.Figure;
import bse202.sda.jigsaw.recources.GameColors;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SpawnerTest {

    @Test
    void respawn() {
        List<Node> figures = new ArrayList<>();
        Pane pane = new Pane();
        for (Figure.FigureType type :
                Figure.FigureType.values()) {
            figures.add(new Figure(type, 40, GameColors.getInstance().SmoothBlue()));
        }

        Pane otherPane = new Pane();
        otherPane.getChildren().add(pane);
        Spawner spawner = new Spawner(pane, figures);

        assertEquals(1, pane.getChildren().size());

        Node parent = pane.getChildren().get(0).getParent();
        while (parent.getParent() != null) {
            assertFalse(parent.isMouseTransparent());
            parent = parent.getParent();
        }

        for (int i = 0; i < 7; i++) {
            spawner.respawn();
        }
        assertEquals(8, spawner.getTotalSpawns());
    }
}