# Ghosts [![Build Status](https://travis-ci.org/VampireHemophile/Ghosts.svg)](https://travis-ci.org/VampireHemophile/Ghosts)
[Ghosts](https://en.wikipedia.org/wiki/Ghosts_%28board_game%29) board game, in Java 8.

## Build & Run
This project uses [Maven](https://maven.apache.org) as its build tool.
Read [Maven in five minutes](https://maven.apache.org/guides/getting-started/maven-in-five-minutes.html) to get a quick introduction to Maven.

```sh
# build:
mvn package

# run (where $version is the project's current version):
java -jar target/Ghosts-0.0.1.jar
```

### Eclipse
To generate an [Eclipse](https://eclipse.org) project, run `mvn eclipse:eclipse`.

## Documentation
To generate the documentation, run `mvn site`. Informations about the project are accessible at `target/site/index.html`, documentation of Ghosts and its tests can be found in the "Project Reports" section.  
An UML diagram can be found in `target/site/apidocs/index.html`, the root of Ghosts' documentation. You need [GraphViz](http://www.graphviz.org/) installed to generate UML diagrams.
