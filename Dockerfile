FROM ubuntu:20.04

RUN apt update && \
    apt install -y openjdk-17-jdk

WORKDIR /app

COPY target/teste-1.0.jar /app/teste-1.0.jar

CMD ["java", "--add-opens", "java.base/java.lang=ALL-UNNAMED", "-jar", "teste-1.0.jar"]
