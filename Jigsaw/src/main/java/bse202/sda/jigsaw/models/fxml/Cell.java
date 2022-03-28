package bse202.sda.jigsaw.models.fxml;

import bse202.sda.jigsaw.interfaces.Action;
import bse202.sda.jigsaw.interfaces.CoordinateTransfer;
import bse202.sda.jigsaw.interfaces.TwoConsumer;
import bse202.sda.jigsaw.utils.IntPoint;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


public class Cell extends Rectangle {
    private BooleanProperty isFilled;
    private final Color initialColor;
    private final Color filledColor;
    private final Color dangerColor;
    private final Color intersectColor;
    private final Color successColor;


    public Action onFigurePlaced;

    public BooleanProperty isFilledProperty() {
        if (this.isFilled == null) {
            isFilled = new SimpleBooleanProperty();
        }
        return isFilled;
    }

    public void setIsFilled(boolean value) {
        isFilledProperty().set(value);
        if (IsFilled()) {
            this.setFill(filledColor);
        } else {
            this.setFill(initialColor);
        }
    }

    public boolean IsFilled() {
        return isFilled != null && isFilled.get();
    }

    public Cell(IntPoint coordinates, double v, double v1,
                Color initialColor, Color successColor, Color filledColor,
                Color dangerColor, Color intersectColor, Field field) {
        super(v, v1, initialColor);

        CellDragHandler dragHandler = new CellDragHandler(field, coordinates);

        this.initialColor = initialColor;
        this.successColor = successColor;
        this.filledColor = filledColor;
        this.dangerColor = dangerColor;
        this.intersectColor = intersectColor;

        this.addEventHandler(MouseDragEvent.MOUSE_DRAG_RELEASED,
                dragHandler::OnDragReleased
        );
        this.addEventHandler(MouseDragEvent.MOUSE_DRAG_ENTERED,
                dragHandler::OnDragEntered
        );
        this.addEventHandler(MouseDragEvent.MOUSE_DRAG_EXITED,
                dragHandler::OnDragExited
        );
    }

    private class CellDragHandler {
        private final Field field;
        private final IntPoint coordinates;

        public CellDragHandler(Field field, IntPoint coordinates) {
            this.field = field;
            this.coordinates = coordinates;
        }

        private void OnDragReleased(MouseEvent e) {
            if (checkBeforePlace()) {
                takeFigureOnField((x, y) ->
                        field.getGrid().get(x).get(y).setIsFilled(true)
                );
                onFigurePlaced.execute();
            }
        }

        private void OnDragEntered(MouseEvent e) {
            if (checkBeforePlace()) {
                takeFigureOnField((x, y) -> field.getGrid().get(x)
                        .get(y).setFill(successColor));
            } else {
                takeFigureOnField((x, y) -> {
                    Cell cell = field.getGrid().get(x).get(y);
                    if (cell.IsFilled()) {
                        cell.setFill(intersectColor);
                    } else {
                        cell.setFill(dangerColor);
                    }
                });
            }
        }

        private void OnDragExited(MouseEvent e) {
            takeFigureOnField((x, y) -> {
                Cell cell = field.getGrid().get(x).get(y);
                if (cell.IsFilled()) {
                    cell.setFill(filledColor);
                } else {
                    cell.setFill(initialColor);
                }
            });
        }

        private void takeFigureOnField(TwoConsumer<Integer, Integer> action) {
            if (field.draggedNode.get() instanceof CoordinateTransfer transfer) {
                if (transfer.transferCoordinates().isPresent()) {
                    for (IntPoint point : transfer.transferCoordinates().get()) {
                        int xToFill = coordinates.getX() + point.getX();
                        int yToFill = coordinates.getY() + point.getY();
                        if (xToFill >= 0 && yToFill >= 0
                                && xToFill < field.getGrid().size() &&
                                yToFill < field.getGrid().get(0).size()) {
                            action.execute(xToFill, yToFill);
                        }
                    }
                }
            }
        }

        private boolean checkBeforePlace() {
            if (field.draggedNode.get() instanceof CoordinateTransfer transfer) {
                if (transfer.transferCoordinates().isPresent()) {
                    for (IntPoint point : transfer.transferCoordinates().get()) {
                        int xToFill = coordinates.getX() + point.getX();
                        int yToFill = coordinates.getY() + point.getY();
                        if (!(xToFill >= 0 && yToFill >= 0
                                && xToFill < field.getGrid().size() &&
                                yToFill < field.getGrid().get(0).size())
                                || field.getGrid().get(xToFill).get(yToFill).IsFilled()) {
                            return false;
                        }
                    }

                    return true;
                }
            }

            throw new IllegalArgumentException("Incorrect coordinates");
        }
    }
}
