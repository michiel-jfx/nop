package nl.dotjava.javafx.components;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import javafx.scene.Node;
import nl.dotjava.javafx.nop.NopApplication;


public class AboutPopup {
    private Stage popupStage;
    private final String imagePath;
    private final int preferredWidth;

    public AboutPopup(String imagePath, int preferredWidth) {
        this.imagePath = imagePath;
        this.preferredWidth = preferredWidth;
    }

    public boolean show(Node parentNode) {
        try {
            // Close existing popup if it's showing
            if (isShowing()) {
                hide();
                return true;
            }

            // Get the parent window
            Window parentWindow = null;
            if (parentNode != null && parentNode.getScene() != null) {
                parentWindow = parentNode.getScene().getWindow();
            }

            // Create a new popup stage
            popupStage = new Stage();
            popupStage.initStyle(StageStyle.TRANSPARENT);
            if (parentWindow != null) {
                popupStage.initOwner(parentWindow);
            }
            popupStage.initModality(Modality.NONE); // Makes it non-blocking

            // Load image
            ImageView imageView = new ImageView(new Image(NopApplication.class.getResourceAsStream(imagePath)));
            imageView.setFitWidth(preferredWidth);
            imageView.setPreserveRatio(true);

            // Create a container with a close button
            Button closeButton = new Button("Close");
            closeButton.setOnAction(e -> hide());

            VBox popupContent = new VBox(10, imageView, closeButton);
            popupContent.setAlignment(Pos.CENTER);
            popupContent.setPadding(new Insets(15));
            popupContent.setStyle("-fx-background-color: white; -fx-background-radius: 10; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 10, 0, 0, 0);");

            // Add a click event on the background to close
            StackPane root = new StackPane(popupContent);
            root.setBackground(new Background(new BackgroundFill(new Color(0, 0, 0, 0.3), CornerRadii.EMPTY, Insets.EMPTY)));
            root.setOnMouseClicked(e -> {
                if (e.getTarget() == root) {
                    hide();
                }
            });

            // Create the scene
            Scene popupScene = new Scene(root);
            popupScene.setFill(Color.TRANSPARENT);
            popupStage.setScene(popupScene);

            // Position in center of parent window if available
            if (parentWindow != null) {
                popupStage.setX(parentWindow.getX() + parentWindow.getWidth()/2 - preferredWidth/2);
                popupStage.setY(parentWindow.getY() + parentWindow.getHeight()/2 - preferredWidth/2);
            } else {
                // Fallback to a fixed size if we can't get the parent window
                popupStage.setWidth(preferredWidth);
                popupStage.setHeight(preferredWidth);
            }

            // Show the popup
            popupStage.show();
            return true;

        } catch (Exception e) {
            System.out.println("Error showing image popup: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean isShowing() {
        return popupStage != null && popupStage.isShowing();
    }

    public void hide() {
        if (isShowing()) {
            popupStage.close();
            popupStage = null;
        }
    }

    public void cleanup() {
        hide();
    }
}
