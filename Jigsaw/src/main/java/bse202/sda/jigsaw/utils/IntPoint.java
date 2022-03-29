package bse202.sda.jigsaw.utils;

/**
 * Simple integer point.
 */
public class IntPoint {
    private int x;
    private int y;

    /**
     * Constructor of point.
     *
     * @param x x coordinate.
     * @param y y coordinate.
     */
    public IntPoint(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Copy constructor.
     *
     * @param other other point.
     */
    public IntPoint(IntPoint other) {
        this.x = other.x;
        this.y = other.y;
    }

    /**
     * X.
     *
     * @return x coordinate.
     */
    public int getX() {
        return x;
    }

    /**
     * Sets x.
     *
     * @param x x to set.
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Y.
     *
     * @return y coordinate.
     */
    public int getY() {
        return y;
    }

    /**
     * Sets y.
     *
     * @param y y to set.
     */
    public void setY(int y) {
        this.y = y;
    }
}
