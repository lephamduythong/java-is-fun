# Spring Boot Maven Template

A Spring Boot project built with Maven, compatible with Java 11.

## Prerequisites

- Java 11 or higher
- Maven 3.6+

## Project Structure

```
springboot-template/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/example/springboottemplate/
│   │   │       ├── SpringbootTemplateApplication.java
│   │   │       └── controller/
│   │   │           └── HelloController.java
│   │   └── resources/
│   │       └── application.properties
│   └── test/
│       └── java/
│           └── com/example/springboottemplate/
│               └── SpringbootTemplateApplicationTests.java
├── pom.xml
└── .gitignore
```

## Technologies

- **Spring Boot**: 2.7.18 (compatible with Java 11)
- **Java**: 11
- **Maven**: Build tool
- **Spring Web**: REST API support

## Getting Started

### 1. Compile the project

```bash
mvn clean compile
```

### 2. Run tests

```bash
mvn test
```

### 3. Run the application

```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

### 4. Test the endpoint

Open a browser or use curl:

```bash
curl http://localhost:8080/
```

You should see: `Hello, Spring Boot with Java 11!`

## Building for Production

Create a WAR file:

```bash
mvn clean package
```

Run the WAR:

```bash
java -jar target/test-api.war
```

## Configuration

Edit [src/main/resources/application.properties](src/main/resources/application.properties) to configure:
- Server port
- Application name
- Other Spring Boot properties

## Project Information

- **Group ID**: com.example
- **Artifact ID**: springboot-template
- **Version**: 0.0.1-SNAPSHOT
- **Java Version**: 11
