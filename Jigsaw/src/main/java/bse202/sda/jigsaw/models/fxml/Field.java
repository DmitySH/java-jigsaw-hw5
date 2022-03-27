package bse202.sda.jigsaw.models.fxml;

import bse202.sda.jigsaw.utils.IntPoint;
import javafx.beans.NamedArg;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class Field extends GridPane {
    public Supplier<Node> draggedNode;

    private final List<List<Cell>> grid;
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

        this.setSnapToPixel(false);
        initGrid();
    }

    private void initGrid() {
        for (int i = 0; i < getRows(); i++) {
            grid.add(new ArrayList<>());
            for (int j = 0; j < getColumns(); j++) {
                Cell box = new Cell(new IntPoint(i, j), 50, 50,
                        Color.GRAY, Color.GREENYELLOW, this);

                grid.get(grid.size() - 1).add(box);
                this.add(box, i, j);
            }
        }
    }

    public List<List<Cell>> getGrid() {
        return grid;
    }
}
