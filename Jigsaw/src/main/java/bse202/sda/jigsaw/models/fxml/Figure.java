package bse202.sda.jigsaw.models.fxml;

import bse202.sda.jigsaw.interfaces.CoordinateTransfer;
import bse202.sda.jigsaw.utils.IntPoint;
import bse202.sda.jigsaw.utils.Rotater2D;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class Figure extends Group implements CoordinateTransfer {
    public enum FigureType {
        FIRST,
        SECOND,
        THIRD,
        FOURTH,
    }

    public static Rotater2D rotater;

    static {
        rotater = new Rotater2D();
    }

    private final int rectSize;
    private final Color rectColor;

    private final HashMap<Rectangle, IntPoint> coordinates;
    private List<IntPoint> clickedRectangleCoordinates;

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
                for (IntPoint point :
                        coordinates.values()) {
                    rotater.rotate(90, point);
                }
            }
            case THIRD -> {
                figureFirst();
                setRotate(180);
                for (IntPoint point :
                        coordinates.values()) {
                    rotater.rotate(180, point);
                }
            }
            case FOURTH -> {
                figureFirst();
                setRotate(270);
                for (IntPoint point :
                        coordinates.values()) {
                    rotater.rotate(270, point);
                }
            }
        }
    }

    private void figureFirst() {
        for (int i = 0; i < 3; i++) {
            Rectangle r = createRectangle();
            r.setY(rectSize * i);
            coordinates.put(r, new IntPoint((int) r.getX() / rectSize,
                    (int) r.getY() / rectSize));
            this.getChildren().add(r);
        }
        Rectangle r = createRectangle();
        r.setX(rectSize);
        coordinates.put(r, new IntPoint((int) r.getX() / rectSize,
                (int) r.getY() / rectSize));

        this.getChildren().add(r);
    }

    private Rectangle createRectangle() {
        Rectangle r = new Rectangle(rectSize, rectSize, rectColor);
        r.setStrokeType(StrokeType.CENTERED);
        r.setStrokeWidth(2);
        r.setStroke(Color.RED);
        r.addEventFilter(MouseEvent.MOUSE_PRESSED,
                e -> calculateCoordinatesFromRectangle(r));
        return r;
    }

    private void calculateCoordinatesFromRectangle(Rectangle r) {
        IntPoint root = new IntPoint(coordinates.get(r));
        List<IntPoint> list = new ArrayList<>(coordinates.values());

        for (IntPoint point : list) {
            point.setX(point.getX() - root.getX());
            point.setY(point.getY() - root.getY());
        }

        clickedRectangleCoordinates = list;
    }

    @Override
    public Optional<List<IntPoint>> transferCoordinates() {
        return Optional.of(clickedRectangleCoordinates);
    }
}
