package nl.dotjava.javafx.components;

import javafx.scene.layout.VBox;
import nl.dotjava.javafx.support.AppLogger;
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

        // 1. Key events
        setOnInputMethodTextChanged(event -> {
            AppLogger.info("Input method changed");
        });
        setOnKeyPressed(event -> {
            AppLogger.info("Key pressed: " + event.getCode());
        });
        setOnKeyReleased(event -> {
            AppLogger.info("Key released: " + event.getCode());
        });
        // Make VBox focusable to receive KeyEvent
        setFocusTraversable(true);

        // 2. Mouse events
        setOnMouseClicked(event -> {
            AppLogger.info("Mouse clicked");
            clickMeasurement.clickPerformed();
            if (clickMeasurement.sameClicks()) {
                notifySameClicks();
            }
        });
        setOnMousePressed(event -> {
            AppLogger.info("Mouse pressed");
        });
        setOnMouseExited(event -> {
            // only useful when mouse is present (and running on the desktop)
            AppLogger.info("Mouse exited");
        });

        // 3. Rotating gesture events
        setOnRotate(event -> {
            AppLogger.info("Rotate gesture detected with angle: " + event.getAngle());
        });
        setOnRotationStarted(event -> {
            AppLogger.info("Rotation gesture started");
        });
        setOnRotationFinished(event -> {
            AppLogger.info("Rotation gesture finished");
        });

        // 4. Swipe Events
        setOnSwipeLeft(event -> {
            notifySwipeLeft();
        });
        setOnSwipeRight(event -> {
            notifySwipeRight();
        });
        setOnSwipeUp(event -> {
            AppLogger.info("Swipe up detected");
        });
        setOnSwipeDown(event -> {
            AppLogger.info("Swipe down detected");
        });

        // 5. Touch events
        setOnTouchPressed(event -> {
            AppLogger.info("Touch pressed, TouchCount = " + event.getTouchCount());
        });
        setOnTouchMoved(event -> {
            AppLogger.info("Touch moved, Coordinates: " + event.getTouchPoint().getX() + ", " + event.getTouchPoint().getY());
        });
        setOnTouchReleased(event -> {
            AppLogger.info("Touch released");
        });

        // 6. Zoom events
        setOnZoom(event -> {
            AppLogger.info("Zoom detected with factor: " + event.getZoomFactor());
        });
        setOnZoomStarted(event -> {
            AppLogger.info("Zoom started");
        });
        setOnZoomFinished(event -> {
            AppLogger.info("Zoom finished");
        });

        // 7. Device rotation events
        sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                newScene.heightProperty().addListener((o, oldH, newH) -> onOrientationChange());
            }
        });
    }

    private void onOrientationChange() {
        if (getScene() != null) {
            double width = getScene().getWidth();
            double height = getScene().getHeight();
            String orientation = width > height ? "landscape" : "portrait";
            AppLogger.info("Device orientation is " + orientation + " [" + (int)width + "x" + (int)height + "]");
        }
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
