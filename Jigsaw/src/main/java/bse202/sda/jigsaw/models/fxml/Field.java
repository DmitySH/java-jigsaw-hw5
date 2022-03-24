package bse202.sda.jigsaw.models.fxml;

import javafx.beans.NamedArg;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Field extends GridPane {
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
        this.setSnapToPixel(false);
        for (int i = 0; i < getRows(); i++) {
            for (int j = 0; j < getColumns(); j++) {
                Rectangle box = new Rectangle(50, 50, Color.GRAY);
                this.add(box, i, j);
            }
        }
    }
}
