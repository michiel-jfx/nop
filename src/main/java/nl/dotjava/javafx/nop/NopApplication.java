package nl.dotjava.javafx.nop;

import com.gluonhq.attach.lifecycle.LifecycleEvent;
import com.gluonhq.attach.lifecycle.LifecycleService;
import com.gluonhq.attach.util.Platform;
import com.gluonhq.attach.util.Services;
import com.gluonhq.charm.glisten.application.AppManager;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.mvc.View;
import javafx.application.Application;
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
import nl.dotjava.javafx.components.AboutPopup;
import nl.dotjava.javafx.components.MainMobilePanel;
import nl.dotjava.javafx.support.MotionEventListener;

import static com.gluonhq.charm.glisten.application.AppManager.HOME_VIEW;
import static javafx.scene.input.KeyCode.BACK_SPACE;
import static javafx.scene.input.KeyCode.ESCAPE;

public class NopApplication extends Application implements MotionEventListener {

    private final AppManager appManager = AppManager.initialize(this::postInit);
    private volatile boolean cleanupAlreadyRun = false;
    private View view;
    private VBox rootVbox;
    private AboutPopup aboutPopup;
    private Stage imagePopupStage;

    @Override
    public void init() {
        System.out.println("***** 1. init");
        cleanupAlreadyRun = false;
        // add shutdown hook early in app lifecycle
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("***** shutdown hook triggered");
            cleanupResources();
        }));
        // setup lifecycle service if available
        setupLifecycleService();

        // load base view
        appManager.addViewFactory(HOME_VIEW, () -> {
            System.out.println("***** javafx version " + System.getProperty("javafx.version") + " on java " + System.getProperty("java.version"));
            rootVbox = new MainMobilePanel();
            rootVbox.setAlignment(Pos.CENTER);
            // register motion listener
            ((MainMobilePanel)rootVbox).addMotionListener(this);

            this.view = new View(rootVbox) {
                @Override
                protected void updateAppBar(AppBar appBar) {
                    appBar.setTitleText("Nop");
                    appBar.setManaged(false);
                    appBar.setVisible(false);
                }
            };
            System.out.println("***** returning view");
            return this.view;
        });
    }

    @Override
    public void start(Stage stage) {
        System.out.println("***** 2. stage start");
        appManager.start(stage);
    }

    private void postInit(Scene scene) {
        System.out.println("***** 3. postInit with scene");
        // add back button handler
        if (Platform.isAndroid()) {
            addBackButtonHandler(scene);
        }
    }

    // add handler for back or escape key at platform level
    private void addBackButtonHandler(Scene scene) {
        System.out.println("***** 4. adding keyhandler to scene");
        scene.setOnKeyPressed(event -> {
            if (event.getCode() == ESCAPE || event.getCode() == BACK_SPACE) {
                System.out.println("***** 5. android go-back ("+event.getCode()+") was pressed");
                handleBackButton();
                event.consume();
            }
        });
    }

    @Override
    public void sameClickEvent() {
        handleBackButton();
    }
    @Override
    public void swipeLeftEvent() {
        System.out.println("***** Swipe Left detected!");
        // add a javafx PopOver with a jpg on it (e.g. new Image(NopApplication.class.getResourceAsStream("/images/appstore.png")); )
        // Create PopOver if it doesn't exist yet
/*
        javafx.application.Platform.runLater(() -> {
            try {
                // Create the popup if needed
                if (aboutPopup == null) {
                    aboutPopup = new AboutPopup("/images/appstore.png", 300);
                }
                aboutPopup.show(view);
            } catch (Exception e) {
                System.out.println("Error handling swipe left: " + e.getMessage());
                e.printStackTrace();
            }
        });
*/

        javafx.application.Platform.runLater(() -> {
            try {
                // Close existing popup if it's showing
                if (imagePopupStage != null && imagePopupStage.isShowing()) {
                    imagePopupStage.close();
                    imagePopupStage = null;
                    return;
                }

                // Create a new popup stage
                imagePopupStage = new Stage();
                imagePopupStage.initStyle(StageStyle.TRANSPARENT);
                imagePopupStage.initOwner(view.getScene().getWindow());
                imagePopupStage.initModality(Modality.NONE); // Makes it non-blocking

                // Load image
                ImageView imageView = new ImageView(new Image(NopApplication.class.getResourceAsStream("/images/appstore.png")));
                imageView.setFitWidth(300);  // Set appropriate size for your image
                imageView.setPreserveRatio(true);

                // Create a container with a close button
                Button closeButton = new Button("Close");
                closeButton.setOnAction(e -> imagePopupStage.close());

                VBox popupContent = new VBox(10, imageView, closeButton);
                popupContent.setAlignment(Pos.CENTER);
                popupContent.setPadding(new Insets(15));
                popupContent.setStyle("-fx-background-color: white; -fx-background-radius: 10; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 10, 0, 0, 0);");

                // Add a click event on the background to close
                StackPane root = new StackPane(popupContent);
                root.setBackground(new Background(new BackgroundFill(
                        new Color(0, 0, 0, 0.3), CornerRadii.EMPTY, Insets.EMPTY)));
                root.setOnMouseClicked(e -> {
                    if (e.getTarget() == root) {
                        imagePopupStage.close();
                    }
                });

                // Create the scene
                Scene popupScene = new Scene(root);
                popupScene.setFill(Color.TRANSPARENT);
                imagePopupStage.setScene(popupScene);

                // Position in center of main window
                Window mainWindow = view.getScene().getWindow();
                imagePopupStage.setX(mainWindow.getX() + mainWindow.getWidth()/2 - 150);
                imagePopupStage.setY(mainWindow.getY() + mainWindow.getHeight()/2 - 150);

                // Show the popup
                imagePopupStage.show();

            } catch (Exception e) {
                System.out.println("Error showing image popup: " + e.getMessage());
                e.printStackTrace();
            }
        });


    }

    @Override
    public void swipeRightEvent() {
        System.out.println("***** Swipe Right detected!");
    }

    private void handleBackButton() {
        System.out.println("***** 6. trying to gracefully exiting the application");
        if (Platform.isAndroid()) {
            cleanupResources();
            System.out.println("***** 7. platform exit (javafx)");
            javafx.application.Platform.exit();
            // try not to use system.exit as it can cause abrupt termination, instead use a more gentle approach through services
            Services.get(LifecycleService.class).ifPresent(service -> {
                System.out.println("***** 8. requesting android activity finish");
                try {
                    service.shutdown();
                } catch (Exception e) {
                    System.out.println("***** error during lifecycle shutdown: " + e);
                }
            });
            // onPause event is triggered (V/GraalActivity)
        }
    }

    @Override
    public void stop() throws Exception {
        System.out.println("***** 9. application stop method called");
        cleanupResources();
        System.out.println("***** 10. calling super.stop()");
        super.stop();
        // pause (lifecycle) event is triggered
        // onStop event is triggered (V/GraalActivity)
        // onDestroy event is triggered  (V/GraalActivity)
    }

    public static void main(String[] args) {
        launch(args);
    }

    private void cleanupResources() {
        if (cleanupAlreadyRun) { return; }
        cleanupAlreadyRun = true;
        // cleanup PopOver
        if (aboutPopup != null) {
            aboutPopup.cleanup();
            aboutPopup = null;
        }

    }

    private void setupLifecycleService() {
        if (Platform.isAndroid()) {
            LifecycleService lifecycleService = Services.get(LifecycleService.class)
                    .orElseThrow(() -> new RuntimeException("***** lifecycle service not available"));

            // when home (or switch application) button was pressed
            lifecycleService.addListener(LifecycleEvent.PAUSE, () -> {
                System.out.println("***** pause lifecycle event");
                cleanupResources();
            });
            // when home button was pressed and app is called back again
            lifecycleService.addListener(LifecycleEvent.RESUME, () -> System.out.println("***** resume lifecycle event"));
        }
    }
}
