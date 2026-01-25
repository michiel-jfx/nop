package nl.dotjava.javafx.nop;

import com.gluonhq.attach.lifecycle.LifecycleEvent;
import com.gluonhq.attach.lifecycle.LifecycleService;
import com.gluonhq.attach.util.Platform;
import com.gluonhq.attach.util.Services;
import com.gluonhq.charm.glisten.application.AppManager;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.mvc.View;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import nl.dotjava.javafx.components.AboutStage;
import nl.dotjava.javafx.components.MainMobilePanel;
import nl.dotjava.javafx.support.AppLogger;
import nl.dotjava.javafx.support.MotionEventListener;

import static com.gluonhq.charm.glisten.application.AppManager.HOME_VIEW;
import static javafx.scene.input.KeyCode.BACK_SPACE;
import static javafx.scene.input.KeyCode.ESCAPE;

public class NopApplication extends Application implements MotionEventListener {

    private final AppManager appManager = AppManager.initialize(this::postInit);
    private volatile boolean cleanupAlreadyRun = false;
    private View view;
    private VBox rootVbox;
    private Stage aboutStage;

    @Override
    public void init() {
        AppLogger.init(true);
        AppLogger.info("1. init");
        cleanupAlreadyRun = false;
        // add shutdown hook early in lifecycle
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            AppLogger.info("Shutdown hook triggered");
            cleanupResources();
        }));
        setupLifecycleService();

        // load base view
        appManager.addViewFactory(HOME_VIEW, () -> {
            AppLogger.info("Javafx version " + System.getProperty("javafx.version") + " on java " + System.getProperty("java.version"));
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
            AppLogger.info("Returning view");
            return this.view;
        });
    }

    @Override
    public void start(Stage stage) {
        AppLogger.info("2. stage start");
        appManager.start(stage);
    }

    private void postInit(Scene scene) {
        AppLogger.info("3. postInit with scene");
        if (Platform.isAndroid()) {
            addBackButtonHandler(scene);
        }
    }

    // add handler for back or escape key at platform level
    private void addBackButtonHandler(Scene scene) {
        AppLogger.info("4. adding keyhandler to scene");
        scene.setOnKeyPressed(event -> {
            if (event.getCode() == ESCAPE || event.getCode() == BACK_SPACE) {
                AppLogger.info("5. android go-back ("+event.getCode()+") was pressed");
                handleBackButton();
                event.consume();
            }
        });
    }

    @Override
    public void sameClickEvent() {
        AppLogger.info("5. three-times-clicked event");
        handleBackButton();
    }

    private void handleBackButton() {
        AppLogger.info("6. trying to gracefully exiting the application");
        if (Platform.isAndroid()) {
            cleanupResources();
            AppLogger.info("7. platform exit (javafx)");
            javafx.application.Platform.exit();
            // try not to use system.exit as it can cause abrupt termination, instead use a more gentle approach through services
            Services.get(LifecycleService.class).ifPresent(service -> {
                AppLogger.info("8. requesting android activity finish");
                try {
                    service.shutdown();
                } catch (Exception e) {
                    AppLogger.error("Error during lifecycle shutdown", e);
                }
            });
            // onPause event is triggered here (V/GraalActivity)
        }
        if (Platform.isDesktop()) {
            cleanupResources();
            AppLogger.info("7. desktop exit (javafx)");
            javafx.application.Platform.exit();
        }
    }

    @Override
    public void stop() throws Exception {
        AppLogger.info("9. application stop method called");
        cleanupResources();
        AppLogger.info("10. calling super.stop()");
        super.stop();
        // also triggered is:
        // pause (lifecycle) event
        // onStop event (V/GraalActivity)
        // onDestroy event (V/GraalActivity)
    }

    @Override
    public void swipeLeftEvent() {
        AppLogger.info("Swipe Left detected");
        showAboutStage();
    }

    @Override
    public void swipeRightEvent() {
        AppLogger.info("Swipe Right detected");
    }

    private void cleanupResources() {
        if (cleanupAlreadyRun) { return; }
        cleanupAlreadyRun = true;
    }

    private void setupLifecycleService() {
        if (Platform.isAndroid()) {
            LifecycleService lifecycleService = Services.get(LifecycleService.class)
                    .orElseThrow(() -> new RuntimeException("***** lifecycle service not available"));

            // when home (or switch application) button was pressed
            lifecycleService.addListener(LifecycleEvent.PAUSE, () -> {
                AppLogger.info("Pause lifecycle event");
                cleanupResources();
            });
            // when home button was pressed and app is called back again
            lifecycleService.addListener(LifecycleEvent.RESUME, () -> AppLogger.info("Resume lifecycle event"));
        }
    }

    private void showAboutStage() {
        javafx.application.Platform.runLater(() -> {
            AppLogger.info("Showing popup");
            try {
                if (aboutStage != null && aboutStage.isShowing()) {
                    aboutStage.close();
                    aboutStage = null;
                    return;
                }
                aboutStage = new AboutStage(view.getScene().getWindow());
                aboutStage.show();
            } catch (Exception e) {
                AppLogger.error("Error showing image popup", e);
                e.printStackTrace();
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
