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

/**
 * Figure class.
 */
public class Figure extends Group implements CoordinateTransfer {
    /**
     * All figure types.
     */
    public enum FigureType {
        FIRST,
        FIRST90,
        FIRST180,
        FIRST270,
        FIRST_REFLECTED,
        FIRST_REFLECTED90,
        FIRST_REFLECTED180,
        FIRST_REFLECTED270,
        SECOND,
        SECOND_REFLECTED,
        SECOND90,
        SECOND_REFLECTED90,
        THIRD,
        THIRD90,
        THIRD180,
        THIRD270,
        FOURTH,
        FOURTH90,
        FOURTH180,
        FOURTH270,
        FIFTH,
        FIFTH90,
        SIXTH,
        SEVENTH,
        SEVENTH90,
        SEVENTH180,
        SEVENTH270,
        EIGHTH,
        EIGHTH90,
        EIGHTH180,
        EIGHTH270,
    }

    /**
     * Rotater.
     */
    public static Rotater2D rotater;

    static {
        rotater = new Rotater2D();
    }

    private final int rectSize;
    private final Color rectColor;

    private final HashMap<Rectangle, IntPoint> coordinates;
    private List<IntPoint> clickedRectangleCoordinates;

    /**
     * Constructor of figure.
     *
     * @param type  type of figure.
     * @param size  size of cell.
     * @param color color of cell.
     */
    public Figure(FigureType type, int size, Color color) {
        super();
        rectSize = size;
        rectColor = color;
        coordinates = new HashMap<>();

        switch (type) {
            case FIRST -> figureFirst(0);
            case FIRST90 -> {
                figureFirst(0);
                setRotate(90);
                for (IntPoint point :
                        coordinates.values()) {
                    rotater.rotate(90, point);
                }
            }
            case FIRST180 -> {
                figureFirst(0);
                setRotate(180);
                for (IntPoint point :
                        coordinates.values()) {
                    rotater.rotate(180, point);
                }
            }
            case FIRST270 -> {
                figureFirst(0);
                setRotate(270);
                for (IntPoint point :
                        coordinates.values()) {
                    rotater.rotate(270, point);
                }
            }
            case FIRST_REFLECTED -> figureFirst(1);
            case FIRST_REFLECTED90 -> {
                figureFirst(1);
                setRotate(90);
                for (IntPoint point :
                        coordinates.values()) {
                    rotater.rotate(90, point);
                }
            }
            case FIRST_REFLECTED180 -> {
                figureFirst(1);
                setRotate(180);
                for (IntPoint point :
                        coordinates.values()) {
                    rotater.rotate(180, point);
                }
            }
            case FIRST_REFLECTED270 -> {
                figureFirst(1);
                setRotate(270);
                for (IntPoint point :
                        coordinates.values()) {
                    rotater.rotate(270, point);
                }
            }
            case SECOND -> figureSecond(0);
            case SECOND_REFLECTED -> figureSecond(1);

            case SECOND90 -> {
                figureSecond(0);
                setRotate(90);
                for (IntPoint point :
                        coordinates.values()) {
                    rotater.rotate(90, point);
                }
            }
            case SECOND_REFLECTED90 -> {
                figureSecond(1);
                setRotate(90);
                for (IntPoint point :
                        coordinates.values()) {
                    rotater.rotate(90, point);
                }
            }
            case THIRD -> figureThird(2);
            case THIRD90 -> {
                figureThird(2);
                setRotate(90);
                for (IntPoint point :
                        coordinates.values()) {
                    rotater.rotate(90, point);
                }
            }
            case THIRD180 -> {
                figureThird(2);
                setRotate(180);
                for (IntPoint point :
                        coordinates.values()) {
                    rotater.rotate(180, point);
                }
            }
            case THIRD270 -> {
                figureThird(2);
                setRotate(270);
                for (IntPoint point :
                        coordinates.values()) {
                    rotater.rotate(270, point);
                }
            }
            case FOURTH -> figureThird(1);
            case FOURTH90 -> {
                figureThird(1);
                setRotate(90);
                for (IntPoint point :
                        coordinates.values()) {
                    rotater.rotate(90, point);
                }
            }
            case FOURTH180 -> {
                figureThird(1);
                setRotate(180);
                for (IntPoint point :
                        coordinates.values()) {
                    rotater.rotate(180, point);
                }
            }
            case FOURTH270 -> {
                figureThird(1);
                setRotate(270);
                for (IntPoint point :
                        coordinates.values()) {
                    rotater.rotate(270, point);
                }
            }
            case FIFTH -> figureFifth();
            case FIFTH90 -> {
                figureFifth();
                setRotate(90);
                for (IntPoint point :
                        coordinates.values()) {
                    rotater.rotate(90, point);
                }
            }
            case SIXTH -> figureSixth();
            case SEVENTH -> figureSeventh();
            case SEVENTH90 -> {
                figureSeventh();
                setRotate(90);
                for (IntPoint point :
                        coordinates.values()) {
                    rotater.rotate(90, point);
                }
            }
            case SEVENTH180 -> {
                figureSeventh();
                setRotate(180);
                for (IntPoint point :
                        coordinates.values()) {
                    rotater.rotate(180, point);
                }
            }
            case SEVENTH270 -> {
                figureSeventh();
                setRotate(270);
                for (IntPoint point :
                        coordinates.values()) {
                    rotater.rotate(270, point);
                }
            }
            case EIGHTH -> figureEighth();
            case EIGHTH90 -> {
                figureEighth();
                setRotate(90);
                for (IntPoint point :
                        coordinates.values()) {
                    rotater.rotate(90, point);
                }
            }
            case EIGHTH180 -> {
                figureEighth();
                setRotate(180);
                for (IntPoint point :
                        coordinates.values()) {
                    rotater.rotate(180, point);
                }
            }
            case EIGHTH270 -> {
                figureEighth();
                setRotate(270);
                for (IntPoint point :
                        coordinates.values()) {
                    rotater.rotate(270, point);
                }
            }
        }
    }

    /**
     * Creates first figure.
     *
     * @param offset to x.
     */
    private void figureFirst(int offset) {
        for (int i = 0; i < 2; i++) {
            Rectangle r = createRectangle();
            r.setX(rectSize * i);
            coordinates.put(r, new IntPoint((int) r.getX() / rectSize,
                    (int) r.getY() / rectSize));
            this.getChildren().add(r);
        }

        for (int i = 1; i < 3; i++) {
            Rectangle r = createRectangle();
            r.setX(rectSize * offset);
            r.setY(rectSize * i);
            coordinates.put(r, new IntPoint((int) r.getX() / rectSize,
                    (int) r.getY() / rectSize));
            this.getChildren().add(r);
        }
    }

    /**
     * Creates second figure.
     *
     * @param offset offset to x.
     */
    private void figureSecond(int offset) {
        for (int i = 0; i < 2; i++) {
            Rectangle r = createRectangle();
            r.setX(offset * rectSize);
            r.setY(rectSize * i);
            coordinates.put(r, new IntPoint((int) r.getX() / rectSize,
                    (int) r.getY() / rectSize));
            this.getChildren().add(r);
        }

        for (int i = 1; i < 3; i++) {
            Rectangle r = createRectangle();
            r.setX(rectSize - offset * rectSize);
            r.setY(rectSize * i);
            coordinates.put(r, new IntPoint((int) r.getX() / rectSize,
                    (int) r.getY() / rectSize));
            this.getChildren().add(r);
        }
    }

    /**
     * Creates third figure.
     *
     * @param offset offset to x.
     */
    private void figureThird(int offset) {
        for (int i = 0; i < 3; i++) {
            Rectangle r = createRectangle();
            r.setX(i * rectSize);
            r.setY(rectSize * 2);
            coordinates.put(r, new IntPoint((int) r.getX() / rectSize,
                    (int) r.getY() / rectSize));
            this.getChildren().add(r);
        }

        for (int i = 0; i < 2; i++) {
            Rectangle r = createRectangle();
            r.setX(offset * rectSize);
            r.setY(rectSize * i);
            coordinates.put(r, new IntPoint((int) r.getX() / rectSize,
                    (int) r.getY() / rectSize));
            this.getChildren().add(r);
        }
    }

    /**
     * Creates fifth figure.
     */
    private void figureFifth() {
        for (int i = 0; i < 3; i++) {
            Rectangle r = createRectangle();
            r.setX(i * rectSize);
            coordinates.put(r, new IntPoint((int) r.getX() / rectSize,
                    (int) r.getY() / rectSize));
            this.getChildren().add(r);
        }
    }

    /**
     * Creates sixth figure.
     */
    private void figureSixth() {
        Rectangle r = createRectangle();
        coordinates.put(r, new IntPoint((int) r.getX() / rectSize,
                (int) r.getY() / rectSize));
        this.getChildren().add(r);
    }

    /**
     * Creates seventh figure.
     */
    private void figureSeventh() {
        for (int i = 0; i < 2; i++) {
            Rectangle r = createRectangle();
            r.setX(i * rectSize);
            coordinates.put(r, new IntPoint((int) r.getX() / rectSize,
                    (int) r.getY() / rectSize));
            this.getChildren().add(r);
        }

        Rectangle r = createRectangle();
        r.setY(rectSize);
        coordinates.put(r, new IntPoint((int) r.getX() / rectSize,
                (int) r.getY() / rectSize));
        this.getChildren().add(r);
    }

    /**
     * Creates eighth figure.
     */
    private void figureEighth() {
        for (int i = 0; i < 3; i++) {
            Rectangle r = createRectangle();
            r.setY(i * rectSize);
            coordinates.put(r, new IntPoint((int) r.getX() / rectSize,
                    (int) r.getY() / rectSize));
            this.getChildren().add(r);
        }

        Rectangle r = createRectangle();
        r.setY(rectSize);
        r.setX(rectSize);
        coordinates.put(r, new IntPoint((int) r.getX() / rectSize,
                (int) r.getY() / rectSize));
        this.getChildren().add(r);
    }

    /**
     * Creates simple rectangle.
     *
     * @return rectangle.
     */
    private Rectangle createRectangle() {
        Rectangle r = new Rectangle(rectSize, rectSize, rectColor);
        r.setStrokeType(StrokeType.CENTERED);
        r.setStrokeWidth(2);
        r.setStroke(Color.rgb(0, 33, 55));
        r.addEventFilter(MouseEvent.MOUSE_PRESSED,
                e -> calculateCoordinatesFromRectangle(r));
        return r;
    }

    /**
     * Calculates coordinates as if rectangle is root.
     *
     * @param r rectangle.
     */
    private void calculateCoordinatesFromRectangle(Rectangle r) {
        IntPoint root = new IntPoint(coordinates.get(r));
        List<IntPoint> list = new ArrayList<>(coordinates.values());

        for (IntPoint point : list) {
            point.setX(point.getX() - root.getX());
            point.setY(point.getY() - root.getY());
        }

        clickedRectangleCoordinates = list;
    }

    /**
     * Allows coordinate transfer.
     *
     * @return coordinates.
     */
    @Override
    public Optional<List<IntPoint>> transferCoordinates() {
        return Optional.of(clickedRectangleCoordinates);
    }
}
