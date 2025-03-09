package nl.dotjava.javafx.nop;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;

public class NopController {

    public NopController() {
        System.out.println("***** NopController instantiated successfully");
    }
    @FXML
    public void initialize() {
        System.out.println("***** NopController initialize method called");
    }
    @FXML void onKeyPressed() {
        System.out.println("***** NopController onKeyPressed method called");
    }
    @FXML void onKeyReleased() {
        System.out.println("***** NopController onKeyReleased method called");
    }
    @FXML void onKeyTyped() {
        System.out.println("***** NopController onKeyTyped method called");
    }
    @FXML void onMouseClicked(MouseEvent event) {
        System.out.println("***** NopController onMouseClicked method called");
    }
    @FXML void onMouseEntered(MouseEvent event) {
        System.out.println("***** NopController onMouseEntered method called");
    }
    @FXML void onMouseExited(MouseEvent event) {
        System.out.println("***** NopController onMouseExited method called");
    }
    @FXML void onMousePressed(MouseEvent event) {
        System.out.println("***** NopController onMousePressed method called");
    }
    @FXML void onMouseReleased(MouseEvent event) {
        System.out.println("***** NopController onMouseReleased method called");
    }
    @FXML void onRotated() {
        System.out.println("***** NopController onRotated method called");
    }
    @FXML void onRotatedStarted() {
        System.out.println("***** NopController onRotatedStarted method called");
    }
    @FXML void onRotatedFinished() {
        System.out.println("***** NopController onRotatedFinished method called");
    }
    @FXML void onSwipeLeft() {
        System.out.println("***** NopController onSwipeLeft method called");
    }
    @FXML void onSwipeRight() {
        System.out.println("***** NopController onSwipeRight method called");
    }
    @FXML void onSwipeUp() {
        System.out.println("***** NopController onSwipeUp method called");
    }
    @FXML void onSwipeDown() {
        System.out.println("***** NopController onSwipeDown method called");
    }
    @FXML void onTouchMoved() {
        System.out.println("***** NopController onTouchMoved method called");
    }
    @FXML void onTouchPressed() {
        System.out.println("***** NopController onTouchPressed method called");
    }
    @FXML void onTouchReleased() {
        System.out.println("***** NopController onTouchReleased method called");
    }
    @FXML void onTouchStationary() {
        System.out.println("***** NopController onTouchStationary method called");
    }
    @FXML void onZoom() {
        System.out.println("***** NopController onZoom method called");
    }
    @FXML void onZoomStarted() {
        System.out.println("***** NopController onZoomStarted method called");
    }
    @FXML void onZoomFinished() {
        System.out.println("***** NopController onZoomFinished method called");
    }
}
