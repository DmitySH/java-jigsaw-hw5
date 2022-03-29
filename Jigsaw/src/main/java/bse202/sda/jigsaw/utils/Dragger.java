package bse202.sda.jigsaw.utils;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class Dragger {
    private enum State {
        ACTIVE,
        INACTIVE
    }

    private final Node target;

    private int parentsToSetTransparent;

    private double anchorX;
    private double anchorY;
    private double mouseOffsetFromNodeZeroX;
    private double mouseOffsetFromNodeZeroY;

    private EventHandler<MouseEvent> setAnchor;
    private EventHandler<MouseEvent> updatePositionOnDrag;
    private EventHandler<MouseEvent> commitPositionOnRelease;
    private EventHandler<MouseEvent> dragDetected;

    private State cycleState = State.INACTIVE;
    private BooleanProperty isDraggable;

    private static Node lastDragged;

    /**
     * Constructor of dragger.
     *
     * @param target target to drag.
     */
    public Dragger(Node target) {
        this(target, false);
    }

    /**
     * Constructor of dragger.
     *
     * @param target      target to drag.
     * @param isDraggable @param allow drag..
     */
    public Dragger(Node target, boolean isDraggable) {
        this.target = target;
        createHandlers();
        createDraggableProperty();
        this.isDraggable.set(isDraggable);

        parentsToSetTransparent = 1;
    }

    /**
     * Creates all drag handlers.
     */
    private void createHandlers() {
        dragDetected = event -> {
            if (event.getButton() != MouseButton.PRIMARY) {
                return;
            }
            target.startFullDrag();
            lastDragged = target;
            event.consume();
        };

        setAnchor = event -> {
            if (event.getButton() != MouseButton.PRIMARY) {
                return;
            }
            if (event.isPrimaryButtonDown()) {
                cycleState = State.ACTIVE;

                anchorX = event.getSceneX();
                anchorY = event.getSceneY();
                mouseOffsetFromNodeZeroX = event.getX();
                mouseOffsetFromNodeZeroY = event.getY();
                target.setMouseTransparent(true);
                changeParentsTransparent(true);
            }
            if (event.isSecondaryButtonDown()) {
                cycleState = State.INACTIVE;
                target.setTranslateX(0);
                target.setTranslateY(0);
            }
        };

        updatePositionOnDrag = event -> {
            if (event.getButton() != MouseButton.PRIMARY) {
                return;
            }
            if (cycleState != State.INACTIVE) {
                target.setTranslateX(event.getSceneX() - anchorX);
                target.setTranslateY(event.getSceneY() - anchorY);
            }
        };

        commitPositionOnRelease = event -> {
            if (event.getButton() != MouseButton.PRIMARY) {
                return;
            }
            if (cycleState != State.INACTIVE) {
                target.setLayoutX(event.getSceneX() - mouseOffsetFromNodeZeroX);
                target.setLayoutY(event.getSceneY() - mouseOffsetFromNodeZeroY);

                target.setTranslateX(0);
                target.setTranslateY(0);

            }
            target.setMouseTransparent(false);
            changeParentsTransparent(false);
        };
    }

    /**
     * Creates draggable flag.
     */
    private void createDraggableProperty() {
        isDraggable = new SimpleBooleanProperty();
        isDraggable.addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                target.addEventFilter(MouseEvent.MOUSE_PRESSED, setAnchor);
                target.addEventFilter(MouseEvent.MOUSE_DRAGGED, updatePositionOnDrag);
                target.addEventFilter(MouseEvent.MOUSE_RELEASED, commitPositionOnRelease);
                target.addEventFilter(MouseEvent.DRAG_DETECTED, dragDetected);
            } else {
                target.removeEventFilter(MouseEvent.MOUSE_PRESSED, setAnchor);
                target.removeEventFilter(MouseEvent.MOUSE_DRAGGED, updatePositionOnDrag);
                target.removeEventFilter(MouseEvent.MOUSE_RELEASED, commitPositionOnRelease);
                target.removeEventFilter(MouseEvent.DRAG_DETECTED, dragDetected);
            }
        });
    }

    /**
     * Simple accessor is draggable.
     *
     * @return is draggable.
     */
    public boolean isIsDraggable() {
        return isDraggable.get();
    }

    /**
     * Gets is draggable.
     *
     * @return is draggable.
     */
    public BooleanProperty isDraggableProperty() {
        return isDraggable;
    }

    /**
     * Sets is draggable.
     *
     * @param value value to set.
     */
    public void setDraggableProperty(boolean value) {
        isDraggable.set(value);
    }

    /**
     * Last dragged node.
     *
     * @return last node was dragged.
     */
    public static Node getLastDragged() {
        return lastDragged;
    }

    /**
     * Sets number of parents to pass events through.
     *
     * @param parentsToSetTransparent number of parents.
     */
    public void setParentsToSetTransparent(int parentsToSetTransparent) {
        this.parentsToSetTransparent = parentsToSetTransparent;
    }

    /**
     * Changes transparent of parents.
     *
     * @param isTransparent value to set.
     */
    private void changeParentsTransparent(boolean isTransparent) {
        Node parent = target.getParent();
        int maxParents = parentsToSetTransparent;
        while (parent.getParent() != null && maxParents > 0) {
            parent.setMouseTransparent(isTransparent);
            parent = parent.getParent();
            --maxParents;
        }
    }
}
