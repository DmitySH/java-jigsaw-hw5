package bse202.sda.jigsaw.controllers;

import bse202.sda.jigsaw.interfaces.CoordinateTransfer;
import bse202.sda.jigsaw.interfaces.TwoConsumer;
import bse202.sda.jigsaw.models.fxml.Cell;
import bse202.sda.jigsaw.models.fxml.Field;
import bse202.sda.jigsaw.models.fxml.Figure;
import bse202.sda.jigsaw.recources.GameColors;
import bse202.sda.jigsaw.utils.Dragger;
import bse202.sda.jigsaw.utils.IntPoint;
import bse202.sda.jigsaw.utils.Spawner;
import bse202.sda.jigsaw.utils.Timer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class GameController implements Initializable {
    private static final int TIMER_SIZE = 70;
    @FXML
    public Field field;
    @FXML
    public Pane pane;
    @FXML
    public HBox mainBox;
    @FXML
    public HBox figuresSpawn;

    @FXML
    public ImageView minutes1;
    @FXML
    public ImageView minutes2;
    @FXML
    public ImageView seconds1;
    @FXML
    public ImageView seconds2;

    @FXML
    public Button stopButton;

    Timer timer;
    Spawner spawner;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        field.draggedNode = Dragger::getLastDragged;
        List<Node> figures = initializeFigures();
        spawner = new Spawner(figuresSpawn, figures);
        initGrid();
        initCells();
        startTimer();
    }

    /**
     * Initializes figures.
     *
     * @return figures.
     */
    private List<Node> initializeFigures() {
        List<Node> figures = new ArrayList<>();

        for (Figure.FigureType type :
                Figure.FigureType.values()) {
            figures.add(new Figure(type, 40, GameColors.getInstance().SmoothBlue()));
            Dragger dg = new Dragger(figures.get(figures.size() - 1));
            dg.setDraggableProperty(true);
        }
        return figures;
    }

    /**
     * Initializes cells.
     */
    private void initCells() {
        List<List<Cell>> grid = field.getGrid();
        for (List<Cell> cells : grid) {
            for (int j = 0; j < grid.get(0).size(); j++) {
                Cell current = cells.get(j);
                current.setOnFigurePlaced(spawner::respawn);
                cells.get(j).addEventHandler(MouseDragEvent.MOUSE_DRAG_RELEASED,
                        e -> cellOnDragReleased(e, current));
                current.addEventHandler(MouseDragEvent.MOUSE_DRAG_ENTERED,
                        e -> cellOnDragEntered(e, current)
                );
                current.addEventHandler(MouseDragEvent.MOUSE_DRAG_EXITED,
                        e -> cellOnDragExited(e, current)
                );
            }
        }
    }

    /**
     * Initializes field.
     */
    private void initGrid() {
        List<List<Cell>> grid = field.getGrid();
        GameColors gameColors = GameColors.getInstance();
        for (int i = 0; i < field.getRows(); i++) {
            grid.add(new ArrayList<>());
            for (int j = 0; j < field.getColumns(); j++) {
                Color primaryColor;

                if (i < 6 && i > 2 && (j < 3 || j > 5)
                        || j < 6 && j > 2 && (i < 3 || i > 5)) {
                    primaryColor = gameColors.SmoothWhite();
                } else {
                    primaryColor = Color.WHITE;
                }

                Cell box = new Cell(new IntPoint(i, j),
                        field.getCellSize(),
                        field.getCellSize(),
                        primaryColor,
                        gameColors.LightGreen(),
                        gameColors.SmoothBlue(),
                        gameColors.BasicPink(),
                        gameColors.DangerRed());
                grid.get(grid.size() - 1).add(box);
                field.add(box, i, j);
            }
        }
    }

    /**
     * Event when drag done.
     *
     * @param e event.
     */
    private void cellOnDragReleased(MouseDragEvent e, Cell cell) {
        if (e.getButton() != MouseButton.PRIMARY) {
            return;
        }
        if (e.isSecondaryButtonDown()) {
            return;
        }
        if (checkBeforePlace(cell.getCoordinates())) {
            takeFigureOnField((x, y) ->
                    field.getGrid().get(x).get(y).setIsFilled(true), cell);
            cell.OnFigurePlaced();
        }
    }

    /**
     * Event when drag entered.
     *
     * @param e event.
     */
    private void cellOnDragEntered(MouseEvent e, Cell cell) {
        if (e.getButton() != MouseButton.PRIMARY) {
            return;
        }
        if (checkBeforePlace(cell.getCoordinates())) {
            takeFigureOnField((x, y) -> field.getGrid().get(x)
                    .get(y).setFill(field.getGrid().get(x).get(y).getSuccessColor()), cell);
        } else {
            takeFigureOnField((x, y) -> {
                Cell current = field.getGrid().get(x).get(y);
                if (current.IsFilled()) {
                    current.setFill(current.getIntersectColor());
                } else {
                    current.setFill(current.getDangerColor());
                }
            }, cell);
        }
    }

    /**
     * Event when drag exited.
     *
     * @param e event.
     */
    private void cellOnDragExited(MouseEvent e, Cell cell) {
        takeFigureOnField((x, y) -> {
            Cell current = field.getGrid().get(x).get(y);
            if (current.IsFilled()) {
                current.setFill(current.getFilledColor());
            } else {
                current.setFill(current.getInitialColor());
            }
        }, cell);
    }

    /**
     * Follows through figure on field.
     *
     * @param action action to execute.
     */
    private void takeFigureOnField(TwoConsumer<Integer, Integer> action, Cell cell) {
        if (field.draggedNode.get() instanceof CoordinateTransfer transfer) {
            if (transfer.transferCoordinates().isPresent()) {
                for (IntPoint point : transfer.transferCoordinates().get()) {
                    int xToFill = cell.getCoordinates().getX() + point.getX();
                    int yToFill = cell.getCoordinates().getY() + point.getY();
                    if (xToFill >= 0 && yToFill >= 0
                            && xToFill < field.getGrid().size() &&
                            yToFill < field.getGrid().get(0).size()) {
                        action.execute(xToFill, yToFill);
                    }
                }
            }
        }
    }

    /**
     * Checks if figure can be placed.
     *
     * @return figure can be placed.
     */
    private boolean checkBeforePlace(IntPoint coordinates) {
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

    /**
     * Action to do on a timer tick.
     */
    private void tickOfTimer() {
        minutes1.setImage(timer.getDigits().get(
                timer.getMinutes() / Timer.BASE));
        minutes2.setImage(timer.getDigits().get(
                timer.getMinutes() % Timer.BASE));
        seconds1.setImage(timer.getDigits().get(
                timer.getSeconds() / Timer.BASE));
        seconds2.setImage(timer.getDigits().get(
                timer.getSeconds() % Timer.BASE));
    }

    /**
     * Starts timer.
     */
    private void startTimer() {
        minutes1.setFitHeight(TIMER_SIZE);
        minutes1.setFitWidth(TIMER_SIZE);
        minutes2.setFitHeight(TIMER_SIZE);
        minutes2.setFitWidth(TIMER_SIZE);
        seconds1.setFitHeight(TIMER_SIZE);
        seconds1.setFitWidth(TIMER_SIZE);
        seconds2.setFitHeight(TIMER_SIZE);
        seconds2.setFitWidth(TIMER_SIZE);

        timer = new Timer(0,
                minutes1, minutes2, seconds1, seconds2);
        timer.setActionOnTick(this::tickOfTimer);
        timer.setActionOnTooLong(this::tooLongTimerAction);
        timer.start();
    }

    /**
     * Happens if play too long.
     */
    private void tooLongTimerAction() {
        Alert alert = createInfoAlert();
        alert.setContentText("You were playing too long... relax...");
        alert.setOnHidden(e -> {
            Stage stage = (Stage) (stopButton.getScene().getWindow());
            stage.close();
        });
        alert.show();
    }

    /**
     * Creates alert with info.
     *
     * @return alert with info.
     */
    private Alert createInfoAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Game info");
        alert.setHeaderText(String.format(
                """
                        Total time spent: %d minutes, %d seconds
                        Total figures placed: %d
                        Cells covered: %d / %d
                        """,
                timer.getMinutes(), timer.getSeconds(),
                spawner.getTotalSpawns() - 1, calculateNumberOfFilled(),
                field.getRows() * field.getColumns()
        ));
        return alert;
    }

    /**
     * Stops current game and shows stat.
     *
     * @param e event.
     */
    public void endGame(ActionEvent e) {
        timer.stop();

        Alert alert = createInfoAlert();
        alert.showAndWait();

        Stage stage = (Stage) (stopButton.getScene().getWindow());
        stage.close();
    }

    /**
     * Finds number of filled cells in a field.
     *
     * @return number of filled cells.
     */
    private int calculateNumberOfFilled() {
        int sum = 0;
        for (int i = 0; i < field.getColumns(); i++) {
            for (int j = 0; j < field.getRows(); j++) {
                if (field.getGrid().get(i).get(j).IsFilled()) {
                    ++sum;
                }
            }
        }

        return sum;
    }
}
