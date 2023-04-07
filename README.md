
# rk1-app


## Requirements

For building and running the application you need:

- [JDK 17](https://openjdk.org/projects/jdk/17/)
- [Gradle](https://gradle.org/)

## Running the application locally

Alternatively you can use the [Spring Boot gradle plugin](https://docs.spring.io/spring-boot/docs/current/gradle-plugin/reference/htmlsingle/) like so:

```shell
./gradlew clean build
./gradlew generateAvroJava
./gradlew bootRun --args='--spring.profiles.active=dev'
```

## Deploying the application to OpenShift


```shell
oc new-app codecentric/springboot-maven3-centos~https://github.com/codecentric/springboot-sample-app
```

This will create:

```shell
docker build -t my-image-name .
docker run -p 8080:8080 my-image-name
docker logs -f my-container-name

```

## Copyright

Released under the Apache License 2.0. See the [LICENSE](https://github.com/codecentric/springboot-sample-app/blob/master/LICENSE) file.

