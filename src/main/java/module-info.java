module nl.dotjava.javafx.nop {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires com.gluonhq.charm.glisten;
    requires com.gluonhq.attach.display;

    opens nl.dotjava.javafx.nop to javafx.fxml;
    exports nl.dotjava.javafx.nop;
}