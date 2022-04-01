package bse202.sda.jigsaw.utils;

import bse202.sda.jigsaw.JigsawGame;
import bse202.sda.jigsaw.interfaces.Action;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Big timer.
 */
public class Timer {
    private static final int SEC_IN_MIN = 60;
    public static final int BASE = 10;
    private static final int MAX_SECONDS = SEC_IN_MIN * 99 + 58;

    private Action actionOnTick;
    private Action actionOnTooLong;

    private int currentSeconds;
    private Timeline timeline;

    private final ArrayList<Image> digits;

    /**
     * Timer constructor.
     *
     * @param startTime initial time.
     * @param iv1       first image view.
     * @param iv2       second image view.
     * @param iv3       third image view.
     * @param iv4       fourth image view.
     */
    public Timer(int startTime,
                 ImageView iv1, ImageView iv2,
                 ImageView iv3, ImageView iv4) {
        currentSeconds = startTime;
        digits = new ArrayList<>();

        initTimer();

        Class<?> clazz = JigsawGame.class;
        InputStream input;
        Image image;
        for (int i = 0; i < 10; i++) {
            input = clazz.getResourceAsStream(
                    String.format("views/images/%d.png", i));

            image = new Image(Objects.requireNonNull(input));
            digits.add(image);
        }

        iv1.setImage(digits.get(getMinutes() / BASE));
        iv2.setImage(digits.get(getMinutes() % BASE));
        iv3.setImage(digits.get(getSeconds() / BASE));
        iv4.setImage(digits.get(getSeconds() % BASE));
    }

    /**
     * Starts timer.
     */
    public void start() {
        timeline.play();
    }

    /**
     * Stops timer.
     */
    public void stop() {
        timeline.stop();
    }

    /**
     * Minutes spent.
     *
     * @return minutes.
     */
    public int getMinutes() {
        return currentSeconds / SEC_IN_MIN;
    }

    /**
     * Seconds spent.
     *
     * @return seconds.
     */
    public int getSeconds() {
        return currentSeconds % SEC_IN_MIN;
    }

    /**
     * Initializes timer.
     */
    private void initTimer() {
        timeline = new Timeline(new KeyFrame(Duration.seconds(1),
                e -> {
                    ++currentSeconds;
                    if (currentSeconds > MAX_SECONDS) {
                        this.stop();
                        actionOnTooLong.execute();
                    }
                    actionOnTick.execute();
                }));
        timeline.setCycleCount(Animation.INDEFINITE);
    }

    /**
     * Sets tick action.
     *
     * @param actionOnTick action on tick.
     */
    public void setActionOnTick(Action actionOnTick) {
        this.actionOnTick = actionOnTick;
    }


    /**
     * Sets tick on long time timer.
     *
     * @param actionOnTooLong action to execute.
     */
    public void setActionOnTooLong(Action actionOnTooLong) {
        this.actionOnTooLong = actionOnTooLong;
    }

    /**
     * Gets digits list.
     *
     * @return digits.
     */
    public ArrayList<Image> getDigits() {
        return digits;
    }
}
