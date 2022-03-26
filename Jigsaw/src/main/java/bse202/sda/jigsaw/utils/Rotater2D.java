package bse202.sda.jigsaw.utils;

import java.util.ArrayList;
import java.util.List;

public class Rotater2D {
    private final List<List<Integer>> angle90;
    private final List<List<Integer>> angle180;
    private final List<List<Integer>> angle270;


    public Rotater2D() {
        angle90 = new ArrayList<>();
        angle180 = new ArrayList<>();
        angle270 = new ArrayList<>();

        for (int i = 0; i < 2; i++) {
            angle90.add(new ArrayList<>());
            angle180.add(new ArrayList<>());
            angle270.add(new ArrayList<>());
        }

        createMatrix(90, angle90);
        createMatrix(180, angle180);
        createMatrix(270, angle270);
    }

    private void createMatrix(double angle, List<List<Integer>> matrix) {
        angle = Math.toRadians(angle);
        matrix.get(0).add((int) Math.cos(angle));
        matrix.get(0).add(-(int) Math.sin(angle));
        matrix.get(1).add((int) Math.sin(angle));
        matrix.get(1).add((int) Math.cos(angle));
    }

    private void matrixProd(List<List<Integer>> matrix, IntPoint vector) {
        IntPoint res = new IntPoint(0, 0);
        res.setX(matrix.get(0).get(0) * vector.getX()
                + matrix.get(0).get(1) * vector.getY());
        res.setY(matrix.get(1).get(0) * vector.getX()
                + matrix.get(1).get(1) * vector.getY());

        vector.setX(res.getX());
        vector.setY(res.getY());
    }

    public void rotate(int angle, IntPoint vector) {
        switch (angle) {
            case 90 -> this.matrixProd(angle90, vector);
            case 180 -> this.matrixProd(angle180, vector);
            case 270 -> this.matrixProd(angle270, vector);
            default -> throw new IllegalArgumentException("Incorrect angle");
        }
    }
}
