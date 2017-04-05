# About the application
This is a Spring Boot application that aids Asset Managers manage their retail shops and consumers to query which shop
is nearest to them. It does so by exposing a REST API, and uses a H2 in memory database as the datastore.

# Building and running this application
This application uses Gradle Wrapper, so the only pre requisite to building and this application is a Java 8 installation
 on your machine.
 After downloading the project from github, do
 ```
 $ ./gradlew build
 ```
 if on Linux or a Mac, or
 ```
 gradlew.bat build
 ```
 if on Windows. This might take a while, especially if you don't have Gradle already. When this is done, do
 ```
 $ java -jar build/libs/asset-manager-api-1.0.0.jar
 ```
 This brings up the in memory H2 instance, as well as spins up an Embedded Tomcat Instance to serve up the app.