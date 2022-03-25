package bse202.sda.jigsaw.models.fxml;

import bse202.sda.jigsaw.utils.IntPoint;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Figure extends Group {
    public enum FigureType {
        FIRST,
        SECOND,
        THIRD,
        FOURTH,
    }

    private final int rectSize;
    private final Color rectColor;

    private HashMap<Rectangle, IntPoint> coordinates;

    public Figure(FigureType type, int size, Color color) {
        super();
        rectSize = size;
        rectColor = color;
        coordinates = new HashMap<>();

        switch (type) {
            case FIRST -> figureFirst();
            case SECOND -> {
                figureFirst();
                setRotate(90);
            }
            case THIRD -> {
                figureFirst();
                setRotate(180);
            }
            case FOURTH -> {
                figureFirst();
                setRotate(270);
            }
        }
    }

    private void figureFirst() {
        for (int i = 0; i < 3; i++) {
            Rectangle r = createRectangle();
            r.setY(rectSize * i);
            coordinates.put(r, new IntPoint((int)r.getX() / rectSize,
                    (int)r.getY() / rectSize));
            this.getChildren().add(r);
        }
        Rectangle r = createRectangle();
        r.setX(rectSize);
        coordinates.put(r, new IntPoint((int)r.getX() / rectSize,
                (int)r.getY() / rectSize));

        this.getChildren().add(r);
    }

    private Rectangle createRectangle() {
        Rectangle r = new Rectangle(rectSize, rectSize, rectColor);
        r.setStrokeType(StrokeType.CENTERED);
        r.setStrokeWidth(2);
        r.setStroke(Color.RED);
        r.addEventFilter(MouseEvent.MOUSE_PRESSED,
                e -> calculateCoordinates(r));
        return r;
    }

    private void calculateCoordinates(Rectangle r){
        IntPoint root = coordinates.get(r);
        List<String> list = new ArrayList<IntPoint>(
                coordinates.values().stream().map(x -> ));
    }

}
