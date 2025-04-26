package nl.dotjava.javafx.support;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * Simple class to measure time between clicks. Goal is to act when both times between three
 * clicks are the same, allowing a certain margin (relative and absolute for greater lengths).
 */
public class ClickMeasurement {

    private static final long MARGIN_TRESHOLD = 5000;
    private static final long FIXED_MARGIN = 2500;
    private LocalDateTime lastClick;
    private long lastDelta;
    private boolean sameDeltas;

    public ClickMeasurement() {
        resetMeasurements();
    }

    public void clickPerformed() {
        LocalDateTime now = LocalDateTime.now();
        long delta = Duration.between(lastClick, now).toMillis();
        long allowedMargin = (this.lastDelta <= MARGIN_TRESHOLD)
                ? Math.round(delta * 0.13)
                : FIXED_MARGIN;
        long difference = Math.abs(delta - lastDelta);
        this.sameDeltas = (difference <= allowedMargin);
        this.lastDelta = delta;
        this.lastClick = now;
    }

    public boolean sameClicks() {
        return this.sameDeltas;
    }

    private void resetMeasurements() {
        this.lastClick = LocalDateTime.now().minusYears(1);
        this.lastDelta = 10000L;
        this.sameDeltas = false;
    }
}
