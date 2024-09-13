# Use the OpenJDK 17 as base image
FROM openjdk:17

# Expose the port the application runs on
EXPOSE 8181

# Copy the jar file to the container
ARG JAR_FILE=build/libs/Focus-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} /focus.jar

# Run the application
ENTRYPOINT ["java","-jar","/focus.jar"]
