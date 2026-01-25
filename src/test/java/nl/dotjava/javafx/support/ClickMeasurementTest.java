package nl.dotjava.javafx.support;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static java.lang.Thread.sleep;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ClickMeasurementTest {

    private ClickMeasurement clickMeasurement;

    @BeforeEach
    void setUp() {
        clickMeasurement = new ClickMeasurement();
    }

    @Test
    void testQuickClickTimings() throws InterruptedException {
        clickMeasurement.clickPerformed();
        sleep(200);
        clickMeasurement.clickPerformed();
        sleep(210);
        clickMeasurement.clickPerformed();
        assertTrue(clickMeasurement.sameClicks());
    }

    @Test
    void testSimilarClickTimings() throws InterruptedException {
        clickMeasurement.clickPerformed();
        sleep(2000);
        clickMeasurement.clickPerformed();
        sleep(2000);
        clickMeasurement.clickPerformed();
        assertTrue(clickMeasurement.sameClicks());
    }

    @Test
    void testDifferentClickTimings() throws InterruptedException {
        clickMeasurement.clickPerformed();
        sleep(2000);
        clickMeasurement.clickPerformed();
        sleep(4000);
        clickMeasurement.clickPerformed();
        assertFalse(clickMeasurement.sameClicks());
    }

    @Test
    void testEdgeCase() throws InterruptedException {
        clickMeasurement.clickPerformed();
        sleep(2000);
        clickMeasurement.clickPerformed();
        // Wait 2.2 seconds (close to threshold)
        sleep(2200);
        clickMeasurement.clickPerformed();
        assertTrue(clickMeasurement.sameClicks());
    }

    @Test
    void testBarelyDifferent() throws InterruptedException {
        clickMeasurement.clickPerformed();
        sleep(2000);
        clickMeasurement.clickPerformed();
        sleep(3100);
        clickMeasurement.clickPerformed();
        assertFalse(clickMeasurement.sameClicks());
    }

    @Test
    void testResetState() throws InterruptedException {
        clickMeasurement.clickPerformed();
        sleep(2000);
        clickMeasurement.clickPerformed();
        sleep(2000);
        clickMeasurement.clickPerformed();
        assertTrue(clickMeasurement.sameClicks());
        clickMeasurement = new ClickMeasurement();
        assertFalse(clickMeasurement.sameClicks());
    }

    @Test
    void testLongIntervalWithinThreshold() throws InterruptedException {
        clickMeasurement.clickPerformed();
        sleep(6000);
        clickMeasurement.clickPerformed();
        sleep(8400);
        clickMeasurement.clickPerformed();
        assertTrue(clickMeasurement.sameClicks());
    }

    @Test
    void testLongIntervalOutsideThreshold() throws InterruptedException {
        clickMeasurement.clickPerformed();
        sleep(6000);
        clickMeasurement.clickPerformed();
        sleep(9500);
        clickMeasurement.clickPerformed();
        assertFalse(clickMeasurement.sameClicks());
    }
}
