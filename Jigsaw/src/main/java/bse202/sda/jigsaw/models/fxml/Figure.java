package bse202.sda.jigsaw.models.fxml;

import bse202.sda.jigsaw.interfaces.Action;
import bse202.sda.jigsaw.interfaces.CoordinateTransfer;
import bse202.sda.jigsaw.recources.GameColors;
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
    public static final int HALF_RIGHT_ROTATE = 90;
    public static final int INVERSE_ROTATE = 180;
    public static final int HALF_LEFT_ROTATE = 270;

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
            case FIRST90 -> rotateFigure(() -> figureFirst(0), HALF_RIGHT_ROTATE);
            case FIRST180 -> rotateFigure(() -> figureFirst(0), INVERSE_ROTATE);
            case FIRST270 -> rotateFigure(() -> figureFirst(0), HALF_LEFT_ROTATE);

            case FIRST_REFLECTED -> figureFirst(1);
            case FIRST_REFLECTED90 -> rotateFigure(() -> figureFirst(1), HALF_RIGHT_ROTATE);
            case FIRST_REFLECTED180 -> rotateFigure(() -> figureFirst(1), INVERSE_ROTATE);
            case FIRST_REFLECTED270 -> rotateFigure(() -> figureFirst(1), HALF_LEFT_ROTATE);

            case SECOND -> figureSecond(0);
            case SECOND_REFLECTED -> figureSecond(1);
            case SECOND90 -> rotateFigure(() -> figureSecond(0), HALF_RIGHT_ROTATE);
            case SECOND_REFLECTED90 -> rotateFigure(() -> figureSecond(1), HALF_RIGHT_ROTATE);

            case THIRD -> figureThird(2);
            case THIRD90 -> rotateFigure(() -> figureThird(2), HALF_RIGHT_ROTATE);
            case THIRD180 -> rotateFigure(() -> figureThird(2), INVERSE_ROTATE);
            case THIRD270 -> rotateFigure(() -> figureThird(2), HALF_LEFT_ROTATE);

            case FOURTH -> figureThird(1);
            case FOURTH90 -> rotateFigure(() -> figureThird(1), HALF_RIGHT_ROTATE);
            case FOURTH180 -> rotateFigure(() -> figureThird(1), INVERSE_ROTATE);
            case FOURTH270 -> rotateFigure(() -> figureThird(1), HALF_LEFT_ROTATE);

            case FIFTH -> figureFifth();
            case FIFTH90 -> rotateFigure(this::figureFifth, HALF_RIGHT_ROTATE);
            case SIXTH -> figureSixth();

            case SEVENTH -> figureSeventh();
            case SEVENTH90 -> rotateFigure(this::figureSeventh, HALF_RIGHT_ROTATE);
            case SEVENTH180 -> rotateFigure(this::figureSeventh, INVERSE_ROTATE);
            case SEVENTH270 -> rotateFigure(this::figureSeventh, HALF_LEFT_ROTATE);

            case EIGHTH -> figureEighth();
            case EIGHTH90 -> rotateFigure(this::figureEighth, HALF_RIGHT_ROTATE);
            case EIGHTH180 -> rotateFigure(this::figureEighth, INVERSE_ROTATE);
            case EIGHTH270 -> rotateFigure(this::figureEighth, HALF_LEFT_ROTATE);
        }
    }

    private void rotateFigure(Action figureCreator, int angle) {
        figureCreator.execute();
        setRotate(angle);
        for (IntPoint point :
                coordinates.values()) {
            rotater.rotate(angle, point);
        }
    }

    /**
     * Creates first figure.
     *
     * @param offset to x.
     */
    private void figureFirst(int offset) {
        horizontalLine();

        for (int i = 1; i < 3; i++) {
            verticalLine(offset, i);
        }
    }


    /**
     * Creates second figure.
     *
     * @param offset offset to x.
     */
    private void figureSecond(int offset) {
        for (int i = 0; i < 2; i++) {
            verticalLine(offset, i);
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
            verticalLine(offset, i);
        }
    }

    /**
     * Draws vertical line
     *
     * @param offset offset.
     * @param i      current position.
     */
    private void verticalLine(int offset, int i) {
        Rectangle r = createRectangle();
        r.setX(rectSize * offset);
        r.setY(rectSize * i);
        coordinates.put(r, new IntPoint((int) r.getX() / rectSize,
                (int) r.getY() / rectSize));
        this.getChildren().add(r);
    }

    /**
     * Draws horizontal line.
     */
    private void horizontalLine() {
        for (int i = 0; i < 2; i++) {
            Rectangle r = createRectangle();
            r.setX(i * rectSize);
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
        horizontalLine();

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
        r.setStroke(GameColors.getInstance().getDarkBlue());
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
