package bse202.sda.jigsaw.utils;

import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

import java.util.List;
import java.util.Random;

public class Spawner {
    private final Pane container;
    private final List<Node> elements;
    private static final Random rnd;

    static {
        rnd = new Random();
    }

    public Spawner(Pane container, List<Node> elements) {
        this.container = container;
        this.elements = elements;
        Node node = elements.get(rnd.nextInt(elements.size()));
        container.getChildren().add(node);
    }

    public void respawn() {
        Node current = container.getChildren().get(0);
        current.setTranslateX(0);
        current.setTranslateY(0);
        current.setMouseTransparent(false);
        makeParentsTransparentFalse();

        container.getChildren().clear();
        Node node = elements.get(rnd.nextInt(elements.size()));
        container.getChildren().add(node);
    }

    private void makeParentsTransparentFalse() {
        Node parent = container.getChildren().get(0).getParent();
        while (parent.getParent() != null) {
            parent.setMouseTransparent(false);
            parent = parent.getParent();
        }
    }
}
