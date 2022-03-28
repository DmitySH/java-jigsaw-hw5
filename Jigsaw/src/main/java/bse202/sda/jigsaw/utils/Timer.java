package bse202.sda.jigsaw.utils;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.control.Labeled;
import javafx.util.Duration;

public class Timer {
    private static final int SEC_IN_MIN = 60;

    private final Labeled label;
    private long currentSeconds;
    private Timeline timeline;

    public Timer(Labeled label, long startTime) {
        this.label = label;
        currentSeconds = startTime;
        label.setText(String.format("Minutes: %d\tSeconds: %d",
                getMinutes(), getSeconds()));
        initTimer();
    }

    public void start() {
        timeline.play();
    }

    public void stop() {
        timeline.stop();
    }

    public long getMinutes() {
        return currentSeconds / SEC_IN_MIN;
    }

    public long getSeconds() {
        return currentSeconds % SEC_IN_MIN;
    }

    private void initTimer() {
        timeline = new Timeline(new KeyFrame(Duration.seconds(1),
                this::tick));
        timeline.setCycleCount(Animation.INDEFINITE);
    }

    private void tick(ActionEvent e) {
        ++currentSeconds;
        label.setText(String.format("Minutes: %d\tSeconds: %d",
                getMinutes(), getSeconds()));
    }

}
