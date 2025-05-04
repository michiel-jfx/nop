# The NOP android app
NOP is the assembly instruction that does nothing; it is the no operation.

Actually, it is close to nothing since the CPU cycles through it, so a tiny very tiny amount of time passes. And it also
uses one bit of space.

Back in the days we used to make demos in assembly and put some NOPs on places in the code which later where altered
(self-modifying code).

This is the app that does nothing, dark-mode version it is ; ) Entirely nothing? Well, it is a skeleton to use for new
mobile applications and all those apps should have a proper exit function, so this Nop app does nothing but exit
nicely. It has a function for it to allow the user to tap three times on the screen and when the same amount of interval
time is between them (with some margin), it will exit the application.

See www.dotjava.nl/nop for background.

## Versions
The mobile app is build with the following versions:

| What                   | Version                                             | See                                                                                            |
|------------------------|-----------------------------------------------------|------------------------------------------------------------------------------------------------|
| Nop mobile application | 0.1                                                 | https://www.dotjava.nl/nop                                                                     |
| GraalVM 23 with Gluon  | native-image 23 2024-09-17 (23+25.1-dev-2409082136) | https://github.com/gluonhq/graal/releases                                                      |
| JavaFX controls        | 24-headless+0-2024-12-02-101029                     | https://mvnrepository.com/artifact/org.openjfx/javafx-controls                                 |
| gluon charm glisten    | 6.2.3                                               | https://nexus.gluonhq.com/nexus/content/repositories/releases/com/gluonhq/charm-glisten/6.2.3/ |
| gluon attach           | 4.0.21                                              | https://nexus.gluonhq.com/nexus/content/repositories/releases/com/gluonhq/attach/              |
| gluonfx maven plugin   | 1.0.25                                              | https://github.com/gluonhq/gluonfx-maven-plugin/                                               |

Why is this important? Before upgrading any artifacts in your project, be sure to make a release of a working version for your various targets (e.g., iOS and Android) and then upgrade the artifacts. Experience tells it's a delicate balance of getting it all together and deploying it on your phone.

## Build and run on your phone (android)
```
mvn clean
rm -rf ~/.gluon/substrate/*
mvn gluonfx:build gluonfx:package -Pandroid
mvn -Pandroid gluonfx:install
mvn -Pandroid -X gluonfx:nativerun
```
This should do the trick.

## Build and run on your iPhone
work in progress

## Information
When you run the application from your laptop, you will see various information on your console. The project has all `System.out` statements on purpose to provide information on what's happening. For example:<br/>
![Image](https://github.com/user-attachments/assets/d9f28f9d-c879-483c-94d1-cdef291a9009)

Also kept in the code on purpose are some unused import statements. These are left for your courtesy and useful when adding a popup or an image.

## Manifest file
In this first version, there only is an android folder with an AndroidManifest file present amongst the famous Nop image art created by my daughter. The manifest file is based on the Gluon helloandroid project, see [GluonHello](https://github.com/gluonhq/gluon-samples/tree/master/HelloGluon). By using Gluon, it is possible to have great interactions with your phone and will also have a popup screen when you start your app when using the commercial free version. 

See the other project [IceConverter](https://github.com/michiel-jfx/iceconverter), which is also a JavaFX mobile phone application but without the Gluon popup. This version uses @FXML only and has fewer possibilities to interact with the mobile phone. It does not have the commercial popup, though.

## Exiting
So as mentioned, the app has the ability to exit. This was done by implementing a motion listener (clicks, touch and motion) in the main application. The MainBoxPanel notifies whichever needs to be informed. In the mobile Nop application, besides the exit signal, also the swipe left and right are already ready to use.

Still, the goal is to keep this repository as clean as possible to function as a base for new mobile projects but also to give an idea of what's possible with some interactions.

# License

The Nop mobile application is released under version 2.0 of the [Apache License](https://www.apache.org/licenses/LICENSE-2.0).
