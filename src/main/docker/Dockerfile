# docker build -t "mukeisoftllc/spring-di-quarkus-example" -f ./src/main/docker/Dockerfile .

FROM ubuntu:20.04

USER root

RUN mkdir spring-di-quarkus-example
WORKDIR spring-di-quarkus-example

COPY src/ ./src/
COPY gradle/ ./gradle/
COPY gradlew ./
COPY gradle.properties ./
COPY settings.gradle ./
COPY build.gradle ./

RUN apt-get update && apt-get install -y --no-install-recommends \
    curl build-essential libz-dev zlib1g-dev git && \
    apt reinstall -y ca-certificates && update-ca-certificates && \
    DEBIAN_FRONTEND=noninteractive apt install -y openjdk-17-jdk-headless && \
    git clone https://github.com/MUKEI-SOFT-LLC/spring-world-ex.git && cd spring-world-ex && git checkout step6 && bash ./gradlew publishToMavenLocal && cd ../ && \
    curl -L https://github.com/graalvm/graalvm-ce-builds/releases/download/jdk-21.0.0/graalvm-community-jdk-21.0.0_linux-x64_bin.tar.gz  | tar zx && \
    JAVA_HOME=graalvm-community-openjdk-21+35.1 ./gradlew build -Dquarkus.package.type=native -x test && cp ./build/*-runner /application

FROM registry.access.redhat.com/ubi8/ubi-minimal:8.8

COPY --from=0 /application ./application

# docker run -p 9000:9000 -it mukeisoftllc/spring-di-quarkus-example /application -Dquarkus.http.host-enabled=false -Dpath.for.texts=/tmp -Dpath.for.images=/tmp -Dlog_dir=/var/log