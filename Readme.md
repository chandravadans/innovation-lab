# About the application
This is a Spring Boot application that aids Asset Managers manage their retail shops and enables customers to query the shop that's nearest to them. It does so by exposing a REST API, and uses a H2 in memory database as the datastore. It uses Google's [Geocode API](https://developers.google.com/maps/documentation/geocoding/intro) to try and figure out the approximate latitude and longitude based on the address given.

# Building and running the application
This application uses [Gradle Wrapper](https://docs.gradle.org/current/userguide/gradle_wrapper.html), so the only pre-requisite to building and this application is having a Java 8 installation on your machine.

 After downloading the project from github, if you're on a Linux or a Mac machine, change to the downloaded folder and do
 ```
 $ ./gradlew build
 ```
 or if on a Windows machine, do
 ```
 gradlew.bat build
 ```
 This might take a while, especially if you don't have Gradle already. When this is done, do
 ```
 $ ./gradlew bootRun
 ```
 or 
 ```
 $ gradlew.bat bootRun
 ```
 depending on which OS you're on.
 
 This brings up the in memory H2 instance and also spins up an embedded Tomcat Instance to serve up the app.
 In order to view the REST API exposed by the app and to play around with it, navigate to the following URL when the app is up and running:
 ```
 http://localhost:8080/swagger-ui.html
 ```
 # Salient features of the application
 * Uses JPA, so the backend store can easily be switched to any SQL based datastore like Oracle, MySQL, etc.
 * Live REST API documentation that uses the [OpenAPI Specification](https://github.com/OAI/OpenAPI-Specification).
 * Clients don't require a REST Client to interact with the app, it hosts an inbuilt page to do the same.
 * Used [slf4j](https://www.slf4j.org), backed by [logback](https://logback.qos.ch) for flexible, lightning fast logging.
 * Externalised YAML based property files for both dev and test environments that allow different configurations for different environments.
 * Ability to interact with the backend database while the app is running through the inbuilt [H2-Console](http://localhost:8080/h2-console) (Works only when the app is up and running)
 * Runtime app health monitoring and metrics reporting at the [Metrics Endpoint](http://localhost:8080/metrics) (Works only when app is running). These can also be exported to a datastore and/or graphing software to visualise them in realtime.
 
 # Troubleshooting
 * On Linux/Mac systems, doing `$chmod +x gradlew` might be necessary before running `gradlew`
