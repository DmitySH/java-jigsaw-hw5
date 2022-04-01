package bse202.sda.jigsaw.models.fxml;

import bse202.sda.jigsaw.recources.GameColors;
import bse202.sda.jigsaw.utils.IntPoint;
import javafx.scene.paint.Color;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CellTest {

    @Test
    void setIsFilled() {
        GameColors gameColors = GameColors.getInstance();
        Cell cell = new Cell(new IntPoint(1, 1),
                23,
                23,
                Color.AZURE,
                gameColors.LightGreen(),
                gameColors.SmoothBlue(),
                gameColors.BasicPink(),
                gameColors.DangerRed());

        assertEquals(cell.getFill(), Color.AZURE);
        assertFalse(cell.IsFilled());

        cell.setIsFilled(true);

        assertEquals(cell.getFill(), gameColors.SmoothBlue());
        assertTrue(cell.IsFilled());
    }
}