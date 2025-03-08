package nl.dotjava.javafx.nop;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class NopApplication extends Application {

    public void start(Stage stage) {
        try {
            Parent root = FXMLLoader.load(NopApplication.class.getResource("nop-view.fxml")); // Adjust path if needed
            Scene scene = new Scene(root, 200, 344);
            stage.setTitle("Nop");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            // Catch and log any exceptions during FXML loading
            System.err.println("Error loading FXML: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
//    @Override
//    public void start(Stage stage) throws IOException {
//        FXMLLoader fxmlLoader = new FXMLLoader(NopApplication.class.getResource("nop-view.fxml"));
//        Scene scene = new Scene(fxmlLoader.load(), 200, 400);
//        stage.setTitle("Nop");
//        stage.setScene(scene);
//        stage.show();
//    }
}