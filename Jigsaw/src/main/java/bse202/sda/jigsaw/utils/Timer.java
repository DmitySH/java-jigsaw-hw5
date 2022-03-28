package bse202.sda.jigsaw.utils;

import bse202.sda.jigsaw.JigsawGame;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Objects;

public class Timer {
    private static final int SEC_IN_MIN = 60;

    private int currentSeconds;
    private Timeline timeline;

    private final ImageView minutes1;
    private final ImageView minutes2;
    private final ImageView seconds1;
    private final ImageView seconds2;

    private final ArrayList<Image> digits;


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

        minutes1 = iv1;
        minutes2 = iv2;
        seconds1 = iv3;
        seconds2 = iv4;

        minutes1.setImage(digits.get(getMinutes() / 10));
        minutes2.setImage(digits.get(getMinutes() % 10));
        seconds1.setImage(digits.get(getSeconds() / 10));
        seconds2.setImage(digits.get(getSeconds() % 10));
    }

    public void start() {
        timeline.play();
    }

    public void stop() {
        timeline.stop();
    }

    public int getMinutes() {
        return currentSeconds / SEC_IN_MIN;
    }

    public int getSeconds() {
        return currentSeconds % SEC_IN_MIN;
    }

    private void initTimer() {
        timeline = new Timeline(new KeyFrame(Duration.seconds(1),
                this::tick));
        timeline.setCycleCount(Animation.INDEFINITE);
    }

    private void tick(ActionEvent e) {
        ++currentSeconds;

        minutes1.setImage(digits.get(getMinutes() / 10));
        minutes2.setImage(digits.get(getMinutes() % 10));
        seconds1.setImage(digits.get(getSeconds() / 10));
        seconds2.setImage(digits.get(getSeconds() % 10));
    }
}
