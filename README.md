# Patronative - Student Record
This is a back-end module of the Patronative application which supports student record management of the Intive Patronage project.
## Table of contents
* [Technologies](#technologies)
* [Features](#features)
* [Swagger](#swagger)
* [Prerequisites](#prerequisites)
* [Installation](#installation)
* [Setup](#setup)
* [Usage](#usage)
* [Status](#status)
## Technologies
* JDK 11 LTS
* Gradle
* Spring Boot 2.4.5
* Springdoc-openapi 1.5
* Lombok
* PostgreSQL
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
* Docker -> https://docs.docker.com/get-docker/
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
4)Run environment containing almost complete set of the application's elements:
`````
docker-compose up -d
`````
5)Create application's image:
````
docker build -t patronative-student-record_app .
````
6) Build the project with Gradle.\
   Run command prompt, go to the project's root directory (patronage21-java-student-record) and type:
````
gradlew build
````
If the build went successful, you will see "BUILD SUCCESSFUL" after the project finishes building. Otherwise, report the problem to developers.
## Setup
Before running application on your own make sure the application started by docker-compose is not running:
````
docker stop patronative-student-record_app
````
To run this project, run command prompt, go to the project's root directory (patronage21-java-student-record), and type the following command:
````
gradlew bootRun
````
If running the project went successful, you will see "Started Patronative Application" as the last log line displayed in the command prompt.\
After that, you will be able to access it at http://localhost:8080.
## Usage
All available endpoints and their parameters can be found in Swagger: http://localhost:8080/swagger-ui.html
### Examples of use
The application must be started first.\
To test this app you can use Swagger or Postman.
#### Swagger
1) Open http://localhost:8080/swagger-ui.html in your web browser.
2) Open one of the controller's tab (e.g. user-controller).
3) Choose the desired operation (e.g. PUT /api/users).
4) In "Parameters" section, click "Try it out".
5) Set desired parameters and click "Execute".

You will see your request and a response from the API below.
#### Postman
You will need Postman (https://www.postman.com/downloads/).
1) Create a new Collection and name it.
2) Click your new Collection with the right mouse button and select "Add request".
3) Choose one of the REST operations and enter the request URL.
4) Click "Send" and you will see a response from the API below.

#### Docker

1) Run container in detached mode calling:

```output
docker-compose up -d
```

2) Check, if container has been created correctly.

To list containers related to image declared in `docker-compose file` call:

```output
docker-compose ps
```

To list all running Docker containers, open your command prompt and call:

```output
docker ps
```

To list all containers (running and stopped), call:

```output
docker ps –a
```

You should be able to see container named ```student_record_db```

3) To list created volumes, call:

```
docker volume ls
```

You should see volume named ```patronage21-java-student-record_pgdata```

4) To see informations about the volume:

```output
docker volume inspect patronage21-java-student-record_pgdata
```

It returns configuration in JSON format:

```
[
    {
        "CreatedAt": "2021-04-09T14:05:56Z",
        "Driver": "local",
        "Labels": {
            "com.docker.compose.project": "patronage21-java-student-record",
            "com.docker.compose.version": "1.28.5",
            "com.docker.compose.volume": "pgdata"
        },
        "Mountpoint": "/var/lib/docker/volumes/patronage21-java-student-record_pgdata/_data",
        "Name": "patronage21-java-student-record_pgdata",
        "Options": null,
        "Scope": "local"
    }
]
```

3) Stop the container:

```output
docker container stop patronative_db
```

Stop and remove all containers created by `docker-compose up`:

```output
docker-compose down
```

4) Remove the container:

```output
docker container rm patronative_db
```

5) Remove the volume. Note volume removal is a separate step.

```output
docker volume rm patronage21-java-student-record_pgdata
```

#### PostgreSQL

Data to access database:

- `SYSTEM`: **PostgreSQL**
- `SERVER`: **postgres**
- `USERNAME`: **admin**
- `PASSWORD`: you can find current password in `application.yml` file
- `DATABASE` **patronative**
- `SCHEMA` **student_record**

To manage database from `IDE` IntelliJ Community Edition, you have to:
1) Activate DB Browser:

- navigate in the `File -> Settings -> Plugins` and type `"Database"`
- Choose and install the `Database Navigator`
- Restart the `IDE`
- Select `View -> Tool windows -> DB Browser`

2) Create new connection:

- click `+`in the top left corner of DB Browser.
- Select `PostgreSQL`
- Now you can see new window with default database settings.
- Provide database credentials from `application.yml` file and click `TEST CONNECTION`button.
- If the test passed, click `Apply` and `OK`, if not, please report it to developers.

3) Now you can get through the database and see`CONSOLES` `SCHEMAS` `USERS`etc.
## Status
This project is still under development.