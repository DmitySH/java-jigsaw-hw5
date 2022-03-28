package bse202.sda.jigsaw.utils;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Labeled;
import javafx.util.Duration;

public class Timer {
    private final Labeled label;
    private long currentSeconds;
    private Timeline timeline;

    public Timer(Labeled label, long startTime) {
        this.label = label;
        currentSeconds = startTime;
        initTimer();
    }

    public void start() {
        timeline.play();
    }

    public void stop() {
        timeline.stop();
    }

    private void initTimer() {
        label.setText(String.valueOf(currentSeconds));
        timeline = new Timeline(new KeyFrame(Duration.seconds(1),
                ev -> label.setText(String.valueOf(++currentSeconds))));
        timeline.setCycleCount(Animation.INDEFINITE);
    }
}
