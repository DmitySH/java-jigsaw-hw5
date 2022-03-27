package bse202.sda.jigsaw.models.fxml;

import bse202.sda.jigsaw.interfaces.CoordinateTransfer;
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

        CellDragHandler dragHandler = new CellDragHandler(coordinates, field);

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

    private record CellDragHandler(IntPoint coordinates,
                                   Field field) {
        private void OnDragReleased(MouseEvent e) {
            System.out.println("Set on " + coordinates.getX() + "  " + coordinates.getY());
            if (field.draggedNode.get() instanceof CoordinateTransfer transfer) {
                if (transfer.transferCoordinates().isPresent()) {
                    for (IntPoint point : transfer.transferCoordinates().get()) {
                        field.getGrid().get(coordinates.getX() + point.getX())
                                .get(coordinates.getY() + point.getY()).setIsFilled(!field.getGrid().get(coordinates.getX() + point.getX())
                                        .get(coordinates.getY() + point.getY()).getIsFilled());
                    }
                }
            }
        }

        private void OnDragEntered(MouseEvent e) {
            if (field.draggedNode.get() instanceof CoordinateTransfer transfer) {
                if (transfer.transferCoordinates().isPresent()) {
                    for (IntPoint point : transfer.transferCoordinates().get()) {
                        field.getGrid().get(coordinates.getX() + point.getX())
                                .get(coordinates.getY() + point.getY()).setOpacity(0.5);
                    }
                }
            }
        }

        private void OnDragExited(MouseEvent e) {
            if (field.draggedNode.get() instanceof CoordinateTransfer transfer) {
                if (transfer.transferCoordinates().isPresent()) {
                    for (IntPoint point : transfer.transferCoordinates().get()) {
                        field.getGrid().get(coordinates.getX() + point.getX())
                                .get(coordinates.getY() + point.getY()).setOpacity(1);
                    }
                }
            }
        }
    }
}
