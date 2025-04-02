package nl.dotjava.javafx.support;

import java.time.Duration;
import java.time.LocalDateTime;

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

        // some loggin' for edgy cases to fine-tune
        if (this.sameDeltas) {
            System.out.println("***** same! difference: "+difference+"ms, allowed: "+allowedMargin+"ms");
        } else {
            System.out.println("***** different intervals. last delta: "+this.lastDelta+"ms, current delta: "+delta+"ms, difference: "+difference+"ms, allowed: "+allowedMargin+"ms");
        }
        this.lastDelta = delta;
        this.lastClick = now;
    }

    public boolean isSameClicks() {
        return this.sameDeltas;
    }

    private void resetMeasurements() {
        // initialize with a last click which differs from a last delta (a lot)
        this.lastClick = LocalDateTime.now().minusYears(1);
        this.lastDelta = 10000L;
        this.sameDeltas = false;
    }
}
