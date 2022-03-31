package bse202.sda.jigsaw.models.fxml;

import bse202.sda.jigsaw.interfaces.Action;
import bse202.sda.jigsaw.utils.IntPoint;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


/**
 * Class of field cell.
 */
public class Cell extends Rectangle {
    private BooleanProperty isFilled;
    private final Color initialColor;
    private final Color filledColor;
    private final Color dangerColor;
    private final Color intersectColor;
    private final Color successColor;
    private final IntPoint coordinates;

    private Action onFigurePlaced;

    public void OnFigurePlaced() {
        onFigurePlaced.execute();
    }

    /**
     * Get filled.
     *
     * @return is filled.
     */
    public BooleanProperty isFilledProperty() {
        if (this.isFilled == null) {
            isFilled = new SimpleBooleanProperty();
        }
        return isFilled;
    }

    /**
     * Sets filled.
     *
     * @param value value to set.
     */
    public void setIsFilled(boolean value) {
        isFilledProperty().set(value);
        if (IsFilled()) {
            this.setFill(filledColor);
        } else {
            this.setFill(initialColor);
        }
    }

    /**
     * Short accessor to is filled.
     *
     * @return is filled.
     */
    public boolean IsFilled() {
        return isFilled != null && isFilled.get();
    }

    /**
     * Cells constructor.
     *
     * @param coordinates    position.
     * @param v              size1.
     * @param v1             size2.
     * @param initialColor   initialColor.
     * @param successColor   successColor.
     * @param filledColor    filledColor.
     * @param dangerColor    dangerColor.
     * @param intersectColor intersectColor.
     */
    public Cell(IntPoint coordinates, double v, double v1,
                Color initialColor, Color successColor, Color filledColor,
                Color dangerColor, Color intersectColor) {
        super(v, v1, initialColor);

        this.coordinates = coordinates;
        this.initialColor = initialColor;
        this.successColor = successColor;
        this.filledColor = filledColor;
        this.dangerColor = dangerColor;
        this.intersectColor = intersectColor;
    }

    /**
     * Gets initial color.
     *
     * @return initial color of a cell.
     */
    public Color getInitialColor() {
        return initialColor;
    }

    /**
     * Gets filled color.
     *
     * @return filled color of a cell.
     */
    public Color getFilledColor() {
        return filledColor;
    }

    /**
     * Gets danger color.
     *
     * @return danger color of a cell.
     */
    public Color getDangerColor() {
        return dangerColor;
    }

    /**
     * Gets intersect color.
     *
     * @return intersect color of a cell.
     */
    public Color getIntersectColor() {
        return intersectColor;
    }

    /**
     * Gets success color.
     *
     * @return success colo of a cell.
     */
    public Color getSuccessColor() {
        return successColor;
    }

    /**
     * Gets coordinates.
     *
     * @return coordinates of a cell.
     */
    public IntPoint getCoordinates() {
        return coordinates;
    }

    /**
     * Setter for action.
     *
     * @param onFigurePlaced value to set.
     */
    public void setOnFigurePlaced(Action onFigurePlaced) {
        this.onFigurePlaced = onFigurePlaced;
    }
}
