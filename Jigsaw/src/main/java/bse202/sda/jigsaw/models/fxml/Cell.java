package bse202.sda.jigsaw.models.fxml;

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


    public BooleanProperty isFilledProperty() {
        if (this.isFilled == null) {
            isFilled = new SimpleBooleanProperty();
        }
        return isFilled;
    }

    public void setIsFilled(boolean value) {
        isFilledProperty().set(value);
        if (getIsFilled()) {
            this.setFill(filledColor);
        } else {
            this.setFill(initialColor);
        }
    }

    public boolean getIsFilled() {
        return isFilled != null && isFilled.get();
    }

    public Cell(IntPoint coordinates, double v, double v1,
                Color initialColor, Color filledColor, Field field) {
        super(v, v1, initialColor);

        CellDragHandler dragHandler = new CellDragHandler(field, coordinates, Color.PINK);

        this.initialColor = initialColor;
        this.filledColor = filledColor;
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
        private final Color dangerColor;

        public CellDragHandler(Field field, IntPoint coordinates, Color dangerColor) {
            this.field = field;
            this.coordinates = coordinates;
            this.dangerColor = dangerColor;
        }

        private void OnDragReleased(MouseEvent e) {
            if (checkBeforePlace()) {
                takeFigureOnField((x, y) ->
                        field.getGrid().get(x).get(y).setIsFilled(true)
                );
            }
        }

        private void OnDragEntered(MouseEvent e) {
            if (checkBeforePlace()) {
                takeFigureOnField((x, y) -> field.getGrid().get(x)
                        .get(y).setOpacity(0.7));
            } else {
                takeFigureOnField((x, y) -> field.getGrid().get(x)
                        .get(y).setFill(dangerColor));
            }
        }

        private void OnDragExited(MouseEvent e) {
            takeFigureOnField((x, y) -> {
                field.getGrid().get(x)
                        .get(y).setOpacity(1);
                field.getGrid().get(x)
                        .get(y).setFill(Cell.this.initialColor);
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
                            if (!field.getGrid().get(xToFill).get(yToFill).getIsFilled()) {
                                action.execute(xToFill, yToFill);
                            }
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
                                || field.getGrid().get(xToFill).get(yToFill).getIsFilled()) {
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
