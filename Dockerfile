FROM openjdk:8-jdk-alpine
EXPOSE 8080
VOLUME /url-shortener
ARG JAR_FILE=target/url-shortener-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} url-shortener.jar
ENTRYPOINT ["java", "-jar", "/url-shortener.jar"]