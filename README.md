# Patronative - Student Record
This is a back-end module of the Patronative application which supports student record management of the Intive Patronage project.
## Table of contents
* [Technologies](#technologies)
* [Features](#features)
* [Swagger](#swagger)
* [Prerequisites](#prerequisites)
* [Installation](#installation)
* [Setup](#setup)
* [Status](#status)
## Technologies
* JDK 11 LTS
* Gradle
* Spring Boot 2.4.5
* Springdoc-openapi 1.5
* Lombok
## Features
The application supports following student record management operations:
* listing candidates and their average grade
* presenting detailed information on the tasks completed by the participants
## Swagger
You can find swagger under following link http://localhost:8080/swagger-ui.html. \
It will be reachable once you start the application.

## Prerequisites
To build and run this project, you will need:
* JDK 11 - installation guide -> https://docs.oracle.com/en/java/javase/11/install
* Gradle -> https://gradle.org/install
* Git -> https://git-scm.com/downloads
## Installation
1) Clone this repo to your desktop in Git Bash:
````
git clone https://github.com/intive/patronage21-java-student-record.git
````
2) Go to it's root directory:
````
cd patronage21-java-student-record
````
3) Change branch to the desired one:
````
git checkout branch_name
````
4) Build the project with Gradle.\
   Run command prompt, go to the project's root directory (patronage21-java-student-record) and type:
````
gradlew build
````
If the build went successful, you will see "BUILD SUCCESSFUL" after the project finishes building. Otherwise, report the problem to developers.
## Setup
To run this project, run command prompt, go to the project's root directory (patronage21-java-student-record), and type the following command:
````
gradlew bootRun
````
If running the project went successful, you will see "Started Patronative Application" as the last log line displayed in the command prompt.\
After that, you will be able to access it at http://localhost:8080.

## Status
This project is still under development.