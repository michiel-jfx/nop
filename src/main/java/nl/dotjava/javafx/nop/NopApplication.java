package nl.dotjava.javafx.nop;

import com.gluonhq.attach.display.DisplayService;
import com.gluonhq.charm.glisten.application.MobileApplication;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.Window;
import nl.dotjava.javafx.support.MainVboxPanel;

import static javafx.scene.input.KeyCode.BACK_SPACE;

public class NopApplication extends MobileApplication {

    @Override
    public void postInit(Scene scene) {
        super.postInit(scene);
        System.out.println("***** postInit scene");

        if (DisplayService.create().isPresent()) {
            System.out.println("DisplayService is available!");
        } else {
            System.err.println("DisplayService is NOT available. Check your configuration!");
        }


        // Update the Stage title dynamically
        Platform.runLater(() -> {
            // Access the Stage via the Scene's window property
            Window window = scene.getWindow();
            if (window instanceof Stage stage) {
                System.out.println("***** Setting title");
                stage.setTitle("Nop");
            }
        });
        VBox vbox = new MainVboxPanel();
        scene.setRoot(vbox);
        addBackButtonHandler(scene);
    }

    // Add handler for Back Button at the platform level
    private void addBackButtonHandler(Scene scene) {
        scene.setOnKeyPressed(event -> {
            if (event.getCode() == BACK_SPACE) {
                System.out.println("***** Back button pressed!");
                handleBackButton();
                event.consume();
            }
        });
    }

    private void handleBackButton() {
        System.out.println("***** Gracefully exiting the application...");
        Platform.exit();
    }

    //public void start(Stage stage) {
        // with the use of your own VBox setup
        //VBox vbox = new MainVboxPanel();
        //Scene scene = new Scene(vbox, 400, 400);
        // Add KeyEvent listener to intercept Back Button presses
        //scene.setOnKeyPressed(event -> {
        //    if (event.getCode() == KeyCode.BACK_SPACE) {
        //        System.out.println("***** Back button pressed!");
        //        // Handle the back button gracefully: either exit the app or perform some action
        //        handleBackButton();
        //        // Consume the event to prevent default behavior
        //        event.consume();
        //    }
        //});
        //stage.setTitle("Nop");
        //stage.setScene(scene);
        //stage.show();
        // with the use of a FXML file
        // try {
        //     Parent root = FXMLLoader.load(NopApplication.class.getResource("nop-view.fxml")); // Adjust path if needed
        //     Scene scene = new Scene(root, 200, 344);
        //     stage.setTitle("Nop");
        //     stage.setScene(scene);
        //     stage.show();
        // } catch (Exception e) {
        //     // Catch and log any exceptions during FXML loading
        //     System.err.println("Error loading FXML: " + e.getMessage());
        //     e.printStackTrace();
        // }
        // or:
        // FXMLLoader fxmlLoader = new FXMLLoader(NopApplication.class.getResource("nop-view.fxml"));
        // Scene scene = new Scene(fxmlLoader.load(), 200, 400);
        // stage.setTitle("Nop");
        // stage.setScene(scene);
        // stage.show();
    //}

    public static void main(String[] args) {
        launch(args);
    }
}