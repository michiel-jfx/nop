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


mvn clean gluonfx:clean
error
mvn -Pandroid gluonfx:build gluonfx:package gluonfx:nativerun

mvn clean gluonfx:build gluonfx:package -Pandroid
mvn -Pandroid gluonfx:install
mvn -Pandroid -X gluonfx:nativerun
all in one
mvn clean gluonfx:build gluonfx:package gluonfx:install gluonfx:nativerun -Pandroid

downgraded to graalvm + gluon 22 final
back to graalvm + gluon 23 dev but now with target 21 instead of 23

downgraded to graalvm 21 but without gluon!
maven set to 3.8.8
java -version:
openjdk version "21.0.2" 2024-01-16
OpenJDK Runtime Environment GraalVM CE 21.0.2+13.1 (build 21.0.2+13-jvmci-23.1-b30)
OpenJDK 64-Bit Server VM GraalVM CE 21.0.2+13.1 (build 21.0.2+13-jvmci-23.1-b30, mixed mode, sharing)

native-image --version :
native-image 21.0.2 2024-01-16
GraalVM Runtime Environment GraalVM CE 21.0.2+13.1 (build 21.0.2+13-jvmci-23.1-b30)
Substrate VM GraalVM CE 21.0.2+13.1 (build 21.0.2+13, serial gc)

mvn -v :
Apache Maven 3.8.8 (4c87b05d9aedce574290d1acc98575ed5eb6cd39)
Maven home: /pub/software/maven-3.8.8
Java version: 21.0.2, vendor: GraalVM Community, runtime: /pub/software/graalvm-community-openjdk-21.0.2+13.1
Default locale: en_US, platform encoding: UTF-8
OS name: "linux", version: "6.8.0-55-generic", arch: "amd64", family: "unix"

mvn clean gluonfx:build gluonfx:package -Pandroid
mvn -Pandroid gluonfx:install
mvn -Pandroid -X gluonfx:nativerun
all in one
mvn clean gluonfx:build gluonfx:package gluonfx:install gluonfx:nativerun -Pandroid

na downgraden foumelding:
[Sun Mar 16 10:07:29 UTC 2025][INFO] [SUB] [1/8] Initializing...                                                                                    (0.0s @ 0.09GB)
[Sun Mar 16 10:07:29 UTC 2025][INFO] [SUB] Error: Could not load CAPCache file. Ensure that options UseCAPCache and NewCAPCache are used on the same version of your application. Raw error: /pub/gitlab/javafx/nop/target/gluonfx/aarch64-android/gvm/capcache/JNIHeaderDirectivesJDK19OrLater.cap (No such file or directory)
[Sun Mar 16 10:07:29 UTC 2025][INFO] [SUB] com.oracle.svm.core.util.UserError$UserException: Could not load CAPCache file. Ensure that options UseCAPCache and NewCAPCache are used on the same version of your application. Raw error: /pub/gitlab/javafx/nop/target/gluonfx/aarch64-android/gvm/capcache/JNIHeaderDirectivesJDK19OrLater.cap (No such file or directory)
[Sun Mar 16 10:07:29 UTC 2025][INFO] [SUB]      at org.graalvm.nativeimage.builder/com.oracle.svm.core.util.UserError.abort(UserError.java:73)

rm -rf target/gluonfx 
dan:
mvn clean gluonfx:build gluonfx:package -Pandroid -Dsvm.newCAPCache=true

weer nieuwe setup want met alleen graalvm (zonder gluon er in) gaat het bouwen met gcc of lvmm helemaal mis...
met graalvm en gluon 23 dev gaat ook niet goed
downgrade naar graalvm 22.1.0.1 final (java 17)
gluonfx plugin naar beneden etc... 1.0.21
- **gluonfx-maven-plugin:** `1.0.21`
- **maven-compiler-plugin:** `3.13.0` (safe choice, should not interfere with current issue)
- **javafx-maven-plugin:** `0.0.8` (stable, should not directly influence the namespace problem)
  | `com.gluonhq:charm-glisten` | `6.1.0` |
  | --- | --- |
  | `com.gluonhq.attach:display` | `4.0.6` |
  | `com.gluonhq.attach:util` | `4.0.6` |
  | `org.openjfx:javafx-controls` | `17.0.2` |
  | `org.openjfx:javafx-fxml` | `17.0.2` |
  | `org.controlsfx:controlsfx` | `11.1.1` |
  | `org.kordamp.bootstrapfx:bootstrapfx-core` | `0.4.0` |
- Java 17 (GraalVM 22.1.0.1 CE).
  GraalVM 22.1.0.1 Java 17 CE with gluonfx-maven-plugin `1.0.21`, you should alig

manifest.xml moet nu packagename hebben :
<?xml version='1.0'?>
<manifest xmlns:android='http://schemas.android.com/apk/res/android'
package='nl.dotjava.javafx.nop'
android:versionCode='1' android:versionName='1.0'>

ook plugin moet 'm hebben:
<plugin>
<groupId>com.gluonhq</groupId>
<artifactId>gluonfx-maven-plugin</artifactId>
<version>${gluonfx.maven.plugin.version}</version>
<extensions>true</extensions>
<configuration>
<verbose>true</verbose>
<target>${gluonfx.target}</target>
<android>
<packageName>nl.dotjava.javafx.nop</packageName>
</android>
<nativeImageArgs>
<arg>--allow-incomplete-classpath</arg>
</nativeImageArgs>
<attachList>
<list>display</list>
<list>util</list>
</attachList>
<reflectionList>
<list>com.gluonhq.attach.util.Services</list>
<list>com.gluonhq.attach.display.impl.DisplayServiceFactory</list>
</reflectionList>
<jniList>com.gluonhq.attach.util.impl.AndroidUtil</jniList>
<mainClass>${main.class}</mainClass>
</configuration>
</plugin>



mvn clean
rm -rf target/*
rm -rf ~/.gluon/substrate/*
ll ~/.gluon/substrate
mvn gluonfx:build gluonfx:package -Pandroid
ls -sla target/gluonfx/aarch64-android/gvm/android_project/app/src/main/jniLibs/
arm64-v8 (which is used in Fairphone 4)

ll $ANDROID_SDK_ROOT/platforms
android-33 (which is used in Faiphone 4)
android-34

$ANDROID_SDK_ROOT/cmdline-tools/latest/bin/sdkmanager --list_installed | grep "platforms;android-33"
platforms;android-33        | 3             | Android SDK Platform 33          | platforms/android-33

in pom.xml en in AndroidManifest.xml the target SDK gezet op API 33

ls ~/.gluon/substrate/*
javafxStaticSdk 24-ea+7.1
javaStaticSdk 24-2.1

mvn -Pandroid gluonfx:install
mvn -Pandroid -X gluonfx:nativerun

toch weer:
<activity android:name='com.gluonhq.helloandroid.MainActivity'
android:exported="true"
android:configChanges="orientation|keyboardHidden">

### how to build and run

mvn clean
rm -rf ~/.gluon/substrate/*
mvn gluonfx:build gluonfx:package -Pandroid
mvn -Pandroid gluonfx:install
mvn -Pandroid -X gluonfx:nativerun

02-04-2024
==========
added click measurement and listener to handle same click event
