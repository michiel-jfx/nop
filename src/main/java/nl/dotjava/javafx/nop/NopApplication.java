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
import nl.dotjava.javafx.components.MainVboxPanel;
import nl.dotjava.javafx.support.ClickListener;

import static com.gluonhq.charm.glisten.application.AppManager.HOME_VIEW;
import static javafx.scene.input.KeyCode.BACK_SPACE;
import static javafx.scene.input.KeyCode.ESCAPE;

public class NopApplication extends Application implements ClickListener {

    private final AppManager appManager = AppManager.initialize(this::postInit);
    private volatile boolean cleanupAlreadyRun = false;
    private View view;
    private VBox rootVbox;

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
            rootVbox = new MainVboxPanel();
            rootVbox.setAlignment(Pos.CENTER);
            ((MainVboxPanel)rootVbox).addSameClickListener(this);

            //Scene scene = new Scene(rootVbox, 2139, 1080);
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
        //Swatch.LIGHT_GREEN.assignTo(scene);
        //scene.getStylesheets().add(NopApplication.class.getResource("/styles.css").toExternalForm());
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