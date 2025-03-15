# the NOP android app
NOP is the assembly instruction that does nothing, it is the nothing operation.

Actually it is close to nothing since the CPU goes pass it, so a tiny very tiny amount of time passes. And it also uses one bit of space.

Back in the eighties we used to make demos (on Amiga 500) in assembly and put some NOPs in on places in the code which where altered later (self modifying code). But that's another story.

This is the app that does nothing. The dark mode version it is.

## darkmode theme
colors: #0A0A0A, #121212, #15252B, #161618, #181818, #192734, #212121, #212124, #22303C, #242526, #282828, #3A3B3C, #404040

## build and run it
mvn -T 2C clean gluonfx:build gluonfx:package -Pandroid   -> 8Gb en 8 threads,  3:45
mvn clean gluonfx:build gluonfx:package -Pandroid         -> 11Gb en 8 threads, 3:38
mvn -Pandroid gluonfx:install
mvn -T 2C -Pandroid gluonfx:install
mvn -Pandroid gluonfx:nativerun
mvn -Pandroid -X gluonfx:nativerun

note: if you don't have a AndroidManifest.xml, then copy it from:
/pub/gitlab/javafx/nop/target/gluonfx/aarch64-android/gvm/android_project/app/src/main/AndroidManifest.xml
to:
/pub/gitlab/javafx/nop/src/android/AndroidManifest.xml
and make changes to fit your needs

## using custom resources

    <resources>
        <resource>
            <directory>src/main/resources</directory>
            <includes>
                <include>**/*.fxml</include>
                <include>**/*.css</include>
            </includes>
        </resource>
    </resources>

not working:
<VBox alignment="CENTER"
prefHeight="2139.0"
prefWidth="1080.0"
spacing="20.0"
xmlns="http://javafx.com/javafx/23.0.1"
xmlns:fx="http://javafx.com/fxml/1"
fx:controller="nl.dotjava.javafx.nop.NopController"
stylesheets="@styles.css">

also not working:
<VBox alignment="CENTER"
prefHeight="2139.0"
prefWidth="1080.0"
spacing="20.0"
xmlns="http://javafx.com/javafx/23.0.1"
xmlns:fx="http://javafx.com/fxml/1"
fx:controller="nl.dotjava.javafx.nop.NopController"
stylesheets="/nl/dotjava/javafx/nop/styles.css">

this one works:
<VBox alignment="CENTER"
prefHeight="2139.0"
prefWidth="1080.0"
spacing="20.0"
xmlns="http://javafx.com/javafx/23.0.1"
xmlns:fx="http://javafx.com/fxml/1"
fx:controller="nl.dotjava.javafx.nop.NopController"
style="-fx-background-color: #15252b;">

## icons
make an 1024x1024 icon then see:  https://www.appicon.co/
