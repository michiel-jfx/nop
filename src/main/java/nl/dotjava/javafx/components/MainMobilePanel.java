package nl.dotjava.javafx.components;

import javafx.scene.layout.VBox;
import nl.dotjava.javafx.support.ClickMeasurement;
import nl.dotjava.javafx.support.MotionEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainMobilePanel extends VBox {
    private final ClickMeasurement clickMeasurement;
    private final List<MotionEventListener> motionEventListeners = new ArrayList<>();

    public MainMobilePanel() {
        setStyle("-fx-background-color: #15252b;");
        setPrefWidth(1080);
        setPrefHeight(2139);
        this.clickMeasurement = new ClickMeasurement();

        // Add EventHandlers
        // 1. Key events
        setOnInputMethodTextChanged(event -> {
            System.out.println("***** Input method changed!");
        });
        setOnKeyPressed(event -> {
            System.out.println("***** Key pressed: " + event.getCode());
        });
        setOnKeyReleased(event -> {
            System.out.println("***** Key released: " + event.getCode());
        });
        // Make VBox focusable to receive KeyEvent
        setFocusTraversable(true);

        // 2. Mouse events
        setOnMouseClicked(event -> {
            //System.out.println("***** Mouse clicked!");
            clickMeasurement.clickPerformed();
            if (clickMeasurement.sameClicks()) {
                notifySameClicks();
            }
        });
        setOnMousePressed(event -> {
            //System.out.println("***** Mouse pressed!");
        });
        setOnMouseExited(event -> {
            //System.out.println("***** Mouse exited!");
        });

        // 3. Rotating events
        setOnRotate(event -> {
            System.out.println("***** Rotate detected!");
        });
        setOnRotationStarted(event -> {
            System.out.println("***** Rotation started detected!");
        });
        setOnRotationFinished(event -> {
            System.out.println("***** Rotation finished detected!");
        });

        // 4. Swipe Events
        setOnSwipeLeft(event -> {
            notifySwipeLeft();
        });
        setOnSwipeRight(event -> {
            notifySwipeRight();
        });
        setOnSwipeUp(event -> {
            System.out.println("***** Swipe Up detected!");
        });
        setOnSwipeDown(event -> {
            System.out.println("***** Swipe Down detected!");
        });

        // 5. Touch events
        // temporarily disabled because much
        setOnTouchMoved(event -> {
            //System.out.println("***** Touch Moved! Coordinates: " + event.getTouchPoint().getX() + ", " + event.getTouchPoint().getY());
        });
        setOnTouchPressed(event -> {
            //System.out.println("***** Touch Pressed! TouchCount = " + event.getTouchCount());
        });
        setOnTouchReleased(event -> {
            //System.out.println("***** Touch Released!");
        });

        // 6. Zoom events
        setOnZoom(event -> {
            System.out.println("***** Zoom detected with factor: " + event.getZoomFactor());
        });
        setOnZoomStarted(event -> {
            System.out.println("***** Zoom started!");
        });
        setOnZoomFinished(event -> {
            System.out.println("***** Zoom finished!");
        });
    }

    public void addMotionListener(MotionEventListener listener) {
        motionEventListeners.add(listener);
    }
    public void removeMotionListener(MotionEventListener listener) {
        motionEventListeners.remove(listener);
    }
    private void notifySameClicks() {
        motionEventListeners.forEach(MotionEventListener::sameClickEvent);
    }
    private void notifySwipeLeft() {
        motionEventListeners.forEach(MotionEventListener::swipeLeftEvent);
    }
    private void notifySwipeRight() {
        motionEventListeners.forEach(MotionEventListener::swipeRightEvent);
    }
}
