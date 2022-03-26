package bse202.sda.jigsaw.models.fxml;

import bse202.sda.jigsaw.utils.IntPoint;
import javafx.beans.NamedArg;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class Field extends GridPane {
    private List<List<Rectangle>> grid;
    public Supplier<Node> draggedNode;

    private IntegerProperty rows;

    public IntegerProperty rowsProperty() {
        if (this.rows == null) {
            rows = new SimpleIntegerProperty();
        }
        return rows;
    }

    public void setRows(int value) {
        rowsProperty().set(value);
    }

    public int getRows() {
        return rows == null ? 0 : rows.get();
    }

    private IntegerProperty columns;

    public IntegerProperty columnsProperty() {
        if (this.columns == null) {
            columns = new SimpleIntegerProperty();
        }
        return columns;
    }

    public void setColumns(int value) {
        columnsProperty().set(value);
    }

    public int getColumns() {
        return columns == null ? 0 : columns.get();
    }


    public Field(@NamedArg("rows") int rows, @NamedArg("columns") int columns) {
        super();
        setRows(rows);
        setColumns(columns);
        grid = new ArrayList<>();
        for (int i = 0; i < rows; i++) {
        }

        this.setSnapToPixel(false);
        for (int i = 0; i < getRows(); i++) {
            grid.add(new ArrayList<>());
            for (int j = 0; j < getColumns(); j++) {
                Rectangle box = new Rectangle(50, 50, Color.GRAY);

                int finalI = i;
                int finalJ = j;
                box.addEventFilter(MouseDragEvent.MOUSE_DRAG_ENTERED,
                        e -> System.out.println(finalI + "  " + finalJ));
                box.addEventFilter(MouseDragEvent.MOUSE_DRAG_RELEASED, e -> {
                    System.out.println("Set on " + finalI + "  " + finalJ);
                    System.out.println(draggedNode.get());
                    if (draggedNode.get().getClass().equals(Figure.class)){
                        Figure fig = (Figure) draggedNode.get();
//                        grid.get(finalI).get(finalJ).setFill(Color.GREENYELLOW);
                        for (IntPoint point: fig.getClickedRectangleCoordinates()) {
                            grid.get(finalI + point.getX())
                                    .get(finalJ + point.getY()).setFill(Color.GREENYELLOW);
                        }
                    }
                });
                grid.get(grid.size() - 1).add(box);
                this.add(box, i, j);
            }
        }
    }
}
