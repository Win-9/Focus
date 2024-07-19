FROM openjdk:17
CMD ["./gradlew", "clean", "build"]
VOLUME /tmp
ARG JAR_FILE=/build/libs/Focus-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} /focus.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar", "focus.jar"]