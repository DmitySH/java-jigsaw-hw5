package bse202.sda.jigsaw.recources;

import javafx.scene.paint.Color;

public class GameColors {
    private static GameColors instance;

    private final Color lightGreen;
    private final Color dangerRed;
    private final Color smoothBlue;
    private final Color smoothWhite;
    private final Color basicPink;
    private final Color darkBlue;


    /**
     * Resource class for game colors.
     */
    private GameColors() {
        lightGreen = Color.rgb(118, 255, 122);
        dangerRed = Color.rgb(227, 18, 14);
        smoothBlue = Color.rgb(0, 149, 182);
        smoothWhite = Color.rgb(228, 231, 240);
        darkBlue = Color.rgb(0, 33, 55);
        basicPink = Color.PINK;
    }

    public static GameColors getInstance() {
        if (instance == null) {
            instance = new GameColors();
        }

        return instance;
    }

    public Color LightGreen() {
        return lightGreen;
    }

    public Color DangerRed() {
        return dangerRed;
    }

    public Color SmoothBlue() {
        return smoothBlue;
    }

    public Color SmoothWhite() {
        return smoothWhite;
    }

    public Color BasicPink() {
        return basicPink;
    }

    public Color getDarkBlue() {
        return darkBlue;
    }
}
