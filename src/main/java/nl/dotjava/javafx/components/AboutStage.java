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

public class AboutStage extends Stage {

    public AboutStage(Window ownerWindow) {
        initStyle(StageStyle.TRANSPARENT);
        initOwner(ownerWindow);
        initModality(Modality.NONE);

        // Load image
        ImageView imageView = new ImageView(new Image(getClass().getResourceAsStream("/images/appstore.png")));
        imageView.setFitWidth(300);
        imageView.setPreserveRatio(true);

        // Create a container with a close button
        Button closeButton = new Button("Close");
        closeButton.setOnAction(e -> close());

        VBox popupContent = new VBox(10, imageView, closeButton);
        popupContent.setAlignment(Pos.CENTER);
        popupContent.setPadding(new Insets(15));
        popupContent.setStyle("-fx-background-color: white; -fx-background-radius: 10; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 10, 0, 0, 0);");

        // Add a click event on the background to close
        StackPane root = new StackPane(popupContent);
        root.setBackground(new Background(new BackgroundFill(new Color(0, 0, 0, 0.3), CornerRadii.EMPTY, Insets.EMPTY)));
        root.setOnMouseClicked(e -> {
            if (e.getTarget() == root) {
                close();
            }
        });

        // Create the scene
        Scene popupScene = new Scene(root);
        popupScene.setFill(Color.TRANSPARENT);
        setScene(popupScene);

        // Position in center of main window
        setX(ownerWindow.getX() + ownerWindow.getWidth() / 2 - 150);
        setY(ownerWindow.getY() + ownerWindow.getHeight() / 2 - 150);
    }
}
