# URL Shortener

🔗 A simple URL Shortener

## Stack

URL Shortener is built using:

* Spring Framework, an application framework for the Java platform.
* Spring Boot, an framework designed to simplify the bootstrapping and development Spring applications.
* Gradle, an open-source build automation system. Feel free to use the `gradlew` executable or `gradlew.cmd` on windows environment.

## Requirements

The following software is required to be installed locally in order to get this project running:

* Java 1.8
* Docker

## Run the project

1. Clone from gitlab (this will create url-shortener in the current directory)
```
git clone https://gitlab.com/diogolundberg/url-shortener.git
```

2. Build the project
```
./gradlew build
```

3. Run the project
```
./gradlew bootRun
```

## Docker installation

### Windows
Donwload it from here: [docker-ce-desktop-windows](https://store.docker.com/editions/community/docker-ce-desktop-windows)

#### Configure Docker environment, in settings:
* General -> Allow **Expose daemon on tcp://localhost:2375 without TLS**

![Expose daemon](https://user-images.githubusercontent.com/4092969/44668835-d5ddd700-a9f4-11e8-893a-117d8470ee7c.png)

* Shared drivers -> **Share C**

![Share C](https://user-images.githubusercontent.com/4092969/44668767-a3cc7500-a9f4-11e8-9798-92b5dd3bd474.png)

### MacOS
Using [homebrew](https://brew.sh/)
```shell
brew cask install docker
```

### Linux
Please follow the instructions according to your linux distribution:

* [centos](https://docs.docker.com/install/linux/docker-ce/centos/)
* [debian](https://docs.docker.com/install/linux/docker-ce/debian/)
* [fedora](https://docs.docker.com/install/linux/docker-ce/fedora/)
* [ubuntu](https://docs.docker.com/install/linux/docker-ce/ubuntu/)

or directly through the [binaries](https://docs.docker.com/install/linux/docker-ce/binaries/)
