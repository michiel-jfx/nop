package nl.dotjava.javafx.nop;

//import com.gluonhq.attach.display.DisplayService;
//import com.gluonhq.charm.glisten.application.MobileApplicationManager;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
//import javafx.stage.Window;
import nl.dotjava.javafx.support.MainVboxPanel;

import static javafx.scene.input.KeyCode.BACK_SPACE;

public class NopApplication extends Application {

//    private MobileApplicationManager app = MobileApplicationManager.initialize(this::postInit);

//    @Override
//    public void init() {
//        AppViewManager.registerViewsAndDrawer();
//        // Retrieve events as soon as possible, so they become available for
//        // notifications delivered when the app is closed
//        Service service = Injector.instantiateModelOrService(Service.class);
//        service.retrieveEvents(true);
//    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        System.out.println("***** Setting title");
        primaryStage.setTitle("Nop");
        // app.start(primaryStage);

        String javaVersion = System.getProperty("java.version");
        String javafxVersion = System.getProperty("javafx.version");
        Label label = new Label("Hello, JavaFX " + javafxVersion + ", running on Java " + javaVersion + ".");

        VBox root = new MainVboxPanel();
        root.setAlignment(Pos.CENTER);
        Scene scene = new Scene(root, 2139, 1080);
        addBackButtonHandler(scene);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

//    public void postInit(Scene scene) {
//        System.out.println("***** postInit scene");
//        VBox vbox = new MainVboxPanel();
//        scene.setRoot(vbox);
//        addBackButtonHandler(scene);
//        if (Services.get(DisplayService.class).isPresent()) {
//            System.out.println("***** DisplayService is available!");
//        } else {
//            System.err.println("***** DisplayService is NOT available. Check your configuration!");
//        }
//        if (DisplayService.create().isPresent()) {
//            System.out.println("***** DisplayService is available!");
//        } else {
//            System.err.println("***** DisplayService is NOT available. Check your configuration!");
//        }
//    }

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

    public static void main(String[] args) {
        launch(args);
    }
}