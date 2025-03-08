package nl.dotjava.javafx.nop;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class NopController {
    public NopController() {
        System.out.println("NopController instantiated successfully!");
    }

    @FXML
    private Label welcomeText;
    @FXML
    private TextField textfieldInput;

    @FXML
    public void initialize() {
        System.out.println("***** NopController initialize method called!");
        if (textfieldInput == null) {
            System.out.println("***** Error: textfieldInput is null! Check fx:id in FXML!");
        } else {
            textfieldInput.focusedProperty().addListener((observable, oldValue, newValue) -> {
                if (!newValue) { // Lost focus
                    onHelloButtonClick();
                }
            });
        }
    }

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("llt í lagi");
    }

    @FXML
    public void onTextSubmit(ActionEvent event) {
        onHelloButtonClick();
    }
}
