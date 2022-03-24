package bse202.sda.jigsaw.models.fxml;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;

public class Figure extends Group {
    public enum FigureType {
        FIRST,
        SECOND,
        THIRD,
        FOURTH,
    }

    private final int rectSize;
    private final Color rectColor;

    public Figure(FigureType type, int size, Color color) {
        super();
        rectSize = size;
        rectColor = color;

        switch (type) {
            case FIRST -> figureFirst();
            case SECOND -> {
                figureFirst();
                setRotate(90);
            }
            case THIRD -> {
                figureFirst();
                setRotate(180);
            }
            case FOURTH -> {
                figureFirst();
                setRotate(270);
            }
        }
    }

    private void figureFirst() {
        for (int i = 0; i < 3; i++) {
            Rectangle r = createRectangle();
            r.setY(rectSize * i);
            this.getChildren().add(r);
        }
        Rectangle r = createRectangle();
        r.setX(rectSize);
        this.getChildren().add(r);
    }

    private Rectangle createRectangle() {
        Rectangle r = new Rectangle(rectSize, rectSize, rectColor);
        r.setStrokeType(StrokeType.CENTERED);
        r.setStrokeWidth(2);
        r.setStroke(Color.RED);

        return r;
    }
}
