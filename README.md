# XML RSS Feed

Its a simple web application, which allows user to enter new Xml Rss Feeds and to view saved ones.

## Getting Started

Project is located in github (), download it using Git or manually download zip file.

### Prerequisites

To run this application there are several things needed:

Java 8 (https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
 
Gradle (https://gradle.org/install/)


### Installing

Open command line or terminal, 
navigate to project folder, where project was Git-cloned (unpacked from zip file)

There should be _gradle.build_ file
```
/rssFeed/
```

Run gradle build command

```
gradle build
```

Run gradle bootRun command

```
gradle bootRun
```

Open your browser and enter following address:

```
localhost:8080
```

## Tests

There is single unit test file with 4 test methods
These methods just verify usage of repository classes using Mockito mocks

## Built With

* [SpringBoot](https://spring.io/projects/spring-boot) - Framework for Spring framework
* [Gradle](https://gradle.org/) - Dependency Management
* [ROME](https://rometools.github.io/rome/) - Used to generate RSS Feeds
* [Thymeleaf](https://www.thymeleaf.org/) - Server-side Java template engine

