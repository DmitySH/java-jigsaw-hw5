package bse202.sda.jigsaw.models.fxml;

import bse202.sda.jigsaw.recources.GameColors;
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

/**
 * Game field.
 */
public class Field extends GridPane {
    public Supplier<Node> draggedNode;

    private final List<List<Cell>> grid;
    private IntegerProperty rows;

    /**
     * Number of rows.
     *
     * @return number of rows.
     */
    public IntegerProperty rowsProperty() {
        if (this.rows == null) {
            rows = new SimpleIntegerProperty();
        }
        return rows;
    }

    /**
     * Sets number of rows.
     *
     * @param value value to set.
     */
    public void setRows(int value) {
        rowsProperty().set(value);
    }

    /**
     * Simple accessor to rows.
     *
     * @return number of rows.
     */
    public int getRows() {
        return rows == null ? 0 : rows.get();
    }

    private IntegerProperty columns;

    /**
     * Number of collumns.
     *
     * @return number of rows.
     */
    public IntegerProperty columnsProperty() {
        if (this.columns == null) {
            columns = new SimpleIntegerProperty();
        }
        return columns;
    }

    /**
     * Sets number of columns.
     *
     * @param value value to set.
     */
    public void setColumns(int value) {
        columnsProperty().set(value);
    }

    /**
     * Simple accessor to columns.
     *
     * @return number of columns.
     */
    public int getColumns() {
        return columns == null ? 0 : columns.get();
    }

    private IntegerProperty cellSize;

    /**
     * Cell size.
     *
     * @return cell size.
     */
    public IntegerProperty cellSizeProperty() {
        if (this.cellSize == null) {
            cellSize = new SimpleIntegerProperty();
        }
        return cellSize;
    }

    /**
     * Sets cell size.
     *
     * @param value value to set.
     */
    public void setCellSize(int value) {
        cellSizeProperty().set(value);
    }

    /**
     * Simple accessor for size.
     *
     * @return cell size.
     */
    public int getCellSize() {
        return cellSize == null ? 0 : cellSize.get();
    }

    /**
     * Constructor of field.
     *
     * @param rows     number of rows.
     * @param columns  number of columns.
     * @param cellSize cell size.
     */
    public Field(@NamedArg("rows") int rows, @NamedArg("columns") int columns,
                 @NamedArg("cellSize") int cellSize) {
        super();
        setRows(rows);
        setColumns(columns);
        setCellSize(cellSize);
        grid = new ArrayList<>();

        this.setSnapToPixel(false);
        initGrid();
    }

    /**
     * Initializes field.
     */
    private void initGrid() {
        GameColors gameColors = GameColors.getInstance();
        for (int i = 0; i < getRows(); i++) {
            grid.add(new ArrayList<>());
            for (int j = 0; j < getColumns(); j++) {
                Color primaryColor;
                if (i < 6 && i > 2 && (j < 3 || j > 5)
                        || j < 6 && j > 2 && (i < 3 || i > 5)) {
                    primaryColor = gameColors.SmoothWhite();
                } else {
                    primaryColor = Color.WHITE;
                }
                Cell box = new Cell(new IntPoint(i, j), getCellSize(), getCellSize(),
                        primaryColor,
                        gameColors.LightGreen(),
                        gameColors.SmoothBlue(),
                        gameColors.BasicPink(),
                        gameColors.DangerRed(),
                        this);
                grid.get(grid.size() - 1).add(box);
                this.add(box, i, j);
            }
        }
    }

    /**
     * Gets grid.
     *
     * @return grid.
     */
    public List<List<Cell>> getGrid() {
        return grid;
    }
}
