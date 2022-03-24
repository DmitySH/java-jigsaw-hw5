package bse202.sda.jigsaw.utils;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

public class Dragger {
    private enum State {
        ACTIVE,
        INACTIVE
    }

    private final Node target;

    private double anchorX;
    private double anchorY;
    private double mouseOffsetFromNodeZeroX;
    private double mouseOffsetFromNodeZeroY;

    private EventHandler<MouseEvent> setAnchor;
    private EventHandler<MouseEvent> updatePositionOnDrag;
    private EventHandler<MouseEvent> commitPositionOnRelease;

    private State cycleState = State.INACTIVE;
    private BooleanProperty isDraggable;

    public Dragger(Node target) {
        this(target, true);
    }

    public Dragger(Node target, boolean isDraggable) {
        this.target = target;
        createHandlers();
        createDraggableProperty();
        this.isDraggable.set(isDraggable);
    }

    private void createHandlers() {
        setAnchor = event -> {
            if (event.isPrimaryButtonDown()) {
                cycleState = State.ACTIVE;

                anchorX = event.getSceneX();
                anchorY = event.getSceneY();
                mouseOffsetFromNodeZeroX = event.getX();
                mouseOffsetFromNodeZeroY = event.getY();
                System.out.println(mouseOffsetFromNodeZeroX);
            }
            if (event.isSecondaryButtonDown()) {
                cycleState = State.INACTIVE;
                target.setTranslateX(0);
                target.setTranslateY(0);
            }
        };

        updatePositionOnDrag = event -> {
            if (cycleState != State.INACTIVE) {
                target.setTranslateX(event.getSceneX() - anchorX);
                target.setTranslateY(event.getSceneY() - anchorY);
            }
        };

        commitPositionOnRelease = event -> {
            if (cycleState != State.INACTIVE) {
                target.setLayoutX(event.getSceneX() - mouseOffsetFromNodeZeroX);
                target.setLayoutY(event.getSceneY() - mouseOffsetFromNodeZeroY);

                target.setTranslateX(0);
                target.setTranslateY(0);
            }
        };
    }

    public void createDraggableProperty() {
        isDraggable = new SimpleBooleanProperty();
        isDraggable.addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                target.addEventFilter(MouseEvent.MOUSE_PRESSED, setAnchor);
                target.addEventFilter(MouseEvent.MOUSE_DRAGGED, updatePositionOnDrag);
                target.addEventFilter(MouseEvent.MOUSE_RELEASED, commitPositionOnRelease);
            } else {
                target.removeEventFilter(MouseEvent.MOUSE_PRESSED, setAnchor);
                target.removeEventFilter(MouseEvent.MOUSE_DRAGGED, updatePositionOnDrag);
                target.removeEventFilter(MouseEvent.MOUSE_RELEASED, commitPositionOnRelease);
            }
        });
    }

    public boolean isIsDraggable() {
        return isDraggable.get();
    }

    public BooleanProperty isDraggableProperty() {
        return isDraggable;
    }

    public void setDraggableProperty(boolean value) {
        isDraggable.set(value);
    }
}
