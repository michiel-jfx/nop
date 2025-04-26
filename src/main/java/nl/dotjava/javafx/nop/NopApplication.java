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
import nl.dotjava.javafx.components.MainBoxPanel;
import nl.dotjava.javafx.support.MotionEventListener;

import static com.gluonhq.charm.glisten.application.AppManager.HOME_VIEW;
import static javafx.scene.input.KeyCode.BACK_SPACE;
import static javafx.scene.input.KeyCode.ESCAPE;

public class NopApplication extends Application implements MotionEventListener {

    private final AppManager appManager = AppManager.initialize(this::postInit);
    private volatile boolean cleanupAlreadyRun = false;
    private View view;
    private VBox rootVbox;

    @Override
    public void init() {
        System.out.println("***** 1. init");
        cleanupAlreadyRun = false;
        // add shutdown hook early in application lifecycle
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("***** shutdown hook triggered");
            cleanupResources();
        }));
        // setup lifecycle service if available
        setupLifecycleService();

        // load base view
        appManager.addViewFactory(HOME_VIEW, () -> {
            System.out.println("***** javafx version " + System.getProperty("javafx.version") + " on java " + System.getProperty("java.version"));
            rootVbox = new MainBoxPanel();
            rootVbox.setAlignment(Pos.CENTER);
            // register motion listener
            ((MainBoxPanel)rootVbox).addMotionListener(this);

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
        // add back or previous button handler
        if (Platform.isAndroid()) {
            addBackButtonHandler(scene);
        }
    }

    // handler for back or escape key at platform level
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
        // swipe left detected in main panel, do some nice stuff
        System.out.println("***** Swipe Left detected!");
    }

    @Override
    public void swipeRightEvent() {
        // swipe right detected in main panel, do some other stuff
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
        // cleanup whatever needs cleaning
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
