package bse202.sda.jigsaw.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Rotater2DTest {

    @Test
    void rotate() {
        Rotater2D rt = new Rotater2D();
        IntPoint p = new IntPoint(3, 5);
        rt.rotate(90, p);
        assertEquals(p.getX(), -5);
        assertEquals(p.getY(), 3);
    }
}