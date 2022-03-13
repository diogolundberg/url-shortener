🔗 A simple URL Shortener API

## Stack

URL Shortener is built using:

* Spring Framework, an application framework for the Java platform.
* Spring Boot, an framework designed to simplify the bootstrapping and development Spring applications.
* Gradle, an open-source build automation system. Feel free to use the `gradlew` on bash or `gradlew.bat` on windows cmd.

## Requirements

The following software is required to be installed locally in order to get this project running:

* Java 1.8
* Docker

## Run the project

1. Make sure you have a functional [docker environment](https://github.com/diogolundberg/url-shortener/wiki/Docker)


2. Clone from github (this will create url-shortener in the current directory)
```
git clone https://github.com/diogolundberg/url-shortener.git
cd url-shortener
```

3. Build the project
```
gradlew build
```

4. Run the project
```
docker-compose up
```

## Try it

```bash
curl -XPOST localhost:8080/shorten -H 'Content-Type: application/json' -d '{
  "url": "https://www.example.com/"
}'
```

[No curl?](https://onlinecurl.com/)
