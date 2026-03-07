# The NOP android app
NOP is the assembly instruction that does nothing, it is the no-operation.

Actually it is close to nothing since the CPU goes pass it, so a tiny very tiny amount of time passes. And it also uses
a small bit of space.

Back in the eighties we used to make demos (on Amiga 500) in assembly and put some NOPs on places in the code which
where altered later (self modifying code). But that's another story.

This is the app that does nothing (dark mode version). It is a skeleton to use for new mobile applications which should
have a proper exit function, so the Nop app exits nicely when you tap three times.

## Versions
The mobile app is built with the following versions:

| What                   | Version               | See                                                                  |
|------------------------|-----------------------|----------------------------------------------------------------------|
| Nop                    | 0.2                   | this, see https://www.dotjava.nl/nop                                 |
| GraalVM 22 with Gluon  | native-image 22.1.0.1 | https://github.com/graalvm/graalvm-ce-builds/releases/tag/jdk-22.0.1 |
| JavaFX controls & fxml | 17.0.17               | https://mvnrepository.com/artifact/org.openjfx/javafx-controls       |
| Controlsfx             | 11.2.2                | https://mvnrepository.com/artifact/org.controlsfx/controlsfx         |
| GluonHQ storage        | 4.0.24                | https://central.sonatype.com/artifact/com.gluonhq.attach/storage     |
| GluonHQ substrated     | 0.0.68 local build    | https://central.sonatype.com/artifact/com.gluonhq/substrate          |
| Gluonfx maven plugin   | 1.0.28                | https://github.com/gluonhq/gluonfx-maven-plugin/                     |
| Javafx maven plugin    | 0.0.8                 | https://mvnrepository.com/artifact/org.openjfx/javafx-maven-plugin   |

It is my experience it's though to find the right combination of versions and get it to work in the Google Play Store.
The previous version used a newer version of GraalVM with Gluon (and a newer version of Java) but failed to pass the
Google Play Console requirements (using a 16Kb pagesize) because the newer version of GraalVM used an older version of
the Gluonfx maven plugin.

## License
The Nop mobile application is released under version 2.0 of the [Apache License](https://www.apache.org/licenses/LICENSE-2.0).

It uses the GraalVM with [Gluon](https://docs.gluonhq.com/#_introduction) included. This means, there will be a popup to
show the non-commercial version of Gluon Mobile is used, see [this](https://gluonhq.com/pricing/) page for more
information on that. If you start building your own opensource mobile application, you can request a one-year license
[here](https://gluonhq.com/programs/free-gluon-licenses/open-source-license-request/). With or without license, it is 
allowed to deploy your app in for exampe the Google Play Store.

## Darkmode theme
This is an experimental full Java, full opensource mobile application to build yourself. Maybe you can customize it 
with one of these other darkmode theme colors #0A0A0A, #121212, #15252B, #161618, #181818, #192734, #212121, #212124,
#22303C, #242526, #282828, #3A3B3C, #404040

(just kidding)

## Build and run (Android)
```
mvn clean
mvn -Pandroid gluonfx:build gluonfx:package
mvn -Pandroid gluonfx:install
mvn -Pandroid gluonfx:nativerun
check_elf_alignment target/gluonfx/aarch64-android/gvm/Nop.apk
check_elf_alignment target/gluonfx/aarch64-android/libNop.so
cp target/gluonfx/aarch64-android/gvm/Nop.aab ~/Downloads/
```

If you find compilation slower than usual, you can try these:
```
mvn -T 2C clean gluonfx:build gluonfx:package -Pandroid
mvn -T 2C -Pandroid gluonfx:install
mvn -Pandroid -X gluonfx:nativerun
```

## Build and run (iPhone)
Still looking for an inexpensive Macbook to generate a version for the iPhone.

## Build and run (Desktop)
Using the `org.openjfx.javafx-maven-plugin` artifact, you can run the app also on your desktop with:

```
mvn gluonfx:run
```

## Quit or exit
So as mentioned, the app has the ability to exit. This was done by implementing a motion listener (clicks, touch and
motion) in the main application. The MainMobilePanel notifies whichever needs to be informed. In the mobile Nop
application, besides the exit signal some other events are ready to use. Still, the goal is to keep this repository as
clean as possible to function as a base for new mobile projects but also to give an idea of what's possible with some
interactions.

## Icons
My daughter made the iconic nop logo, to get the various resolutions like the 1024x1024 one, use [appicon](https://www.appicon.co/).

## History

| When       | What                                                          |
|------------|---------------------------------------------------------------|
| 02-04-2025 | added click measurement and listener to handle 3x click event |
| 26-04-2025 | added about popup panel with jpg on swipe left                |
| 25-01-2026 | improvement on orientation detection                          |
| 06-03-2026 | Google Play Store deployment                                  |
