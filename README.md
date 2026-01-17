# the NOP android app
NOP is the assembly instruction that does nothing, it is the no-operation.

Actually it is close to nothing since the CPU goes pass it, so a tiny very tiny amount of time passes. And it also uses one bit of space.

Back in the eighties we used to make demos (on Amiga 500) in assembly and put some NOPs in on places in the code which where altered later (self modifying code). But that's another story.

This is the app that does nothing. The dark mode version it is.

## Versions
The mobile app is built with the following versions:

| What                   | Version               | See                                                                  |
|------------------------|-----------------------|----------------------------------------------------------------------|
| Nop                    | 0.1                   | this, see https://www.dotjava.nl/iceco                               |
| GraalVM 22 with Gluon  | native-image 22.1.0.1 | https://github.com/graalvm/graalvm-ce-builds/releases/tag/jdk-22.0.1 |
| JavaFX controls & fxml | 17.0.17               | https://mvnrepository.com/artifact/org.openjfx/javafx-controls       |
| Controlsfx             | 11.2.2                | https://mvnrepository.com/artifact/org.controlsfx/controlsfx         |
| GluonHQ storage        | 4.0.24                | https://central.sonatype.com/artifact/com.gluonhq.attach/storage     |
| GluonHQ substrated     | 0.0.68 local build    | https://central.sonatype.com/artifact/com.gluonhq/substrate          |
| Gluonfx maven plugin   | 1.0.28                | https://github.com/gluonhq/gluonfx-maven-plugin/                     |
| Javafx maven plugin    | 0.0.8                 | https://mvnrepository.com/artifact/org.openjfx/javafx-maven-plugin   |
It is my experience it's though to find the right combination of versions and get it to work in the Google Play Store.
The previous version used a newer version of GraalVM (and a newer version of Java) but failed to pass the Google Play
Console requirements (using 16Kb pagesize) because the newer version of GraalVM used an older version of the Gluonfx
maven plugin.

## darkmode theme
colors: #0A0A0A, #121212, #15252B, #161618, #181818, #192734, #212121, #212124, #22303C, #242526, #282828, #3A3B3C, #404040

## build and run it
```
mvn -T 2C clean gluonfx:build gluonfx:package -Pandroid   -> 8Gb en 8 threads,  3:45
mvn clean gluonfx:build gluonfx:package -Pandroid         -> 11Gb en 8 threads, 3:38
mvn -Pandroid gluonfx:install
mvn -T 2C -Pandroid gluonfx:install
mvn -Pandroid gluonfx:nativerun
mvn -Pandroid -X gluonfx:nativerun
```

note: if you don't have a AndroidManifest.xml, then copy it from:
/pub/gitlab/javafx/nop/target/gluonfx/aarch64-android/gvm/android_project/app/src/main/AndroidManifest.xml
to:
/pub/gitlab/javafx/nop/src/android/AndroidManifest.xml
and make changes to fit your needs

## using custom resources
```
<resources>
    <resource>
        <directory>src/main/resources</directory>
        <includes>
            <include>**/*.fxml</include>
            <include>**/*.css</include>
        </includes>
    </resource>
</resources>
```

## icons
make the 1024x1024 icon then see:  https://www.appicon.co/

## History
| When       | What                                                          |
|------------|---------------------------------------------------------------|
| 02-04-2025 | added click measurement and listener to handle 3x click event |
| 26-04-2025 | added popup panel with jpg on swipe left                      |
