FROM openjdk:17-jdk-slim
COPY target/bank-0.0.1-SNAPSHOT.jar bank-0.0.1.jar
ENTRYPOINT ["java","-Dspring.profiles.active=docker","-jar", "/bank-0.0.1.jar"]