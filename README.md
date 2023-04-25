## Info
This application purpose is to manage cities data.
Cities are loaded from csv file and **right now** contain data of id, name and link to image of the city.
File can be found under project's main resources folder.

Functionalities of application allow for:
- returning paginated list of all cities available
- returning single city data
- updating name and image link of choosen city

## Prepare environment
To run this repository locally:
- install OpenJDK for Java 17
- install Gradle 8.0.2 (optional)

*If you are using IntelliJ IDEA, IntelliJ Lombok plugin may be needed too.
So IDE won't underline in red annotations and shouldn't miss anything.*

## Build and run project with Gradle
After everything is installed run to build:
```
gradle build
```
And to launch app:
```
gradle bootRun
```
## Build and run project without Gradle
If you don't want to install Gradle and don't care about its version, use commands below instead.
Those will run gradle wrapper attached to the project.

To build:
```
./gradlew build
```
And to launch project:
```
./gradlew bootRun
```
