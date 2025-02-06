# Use an official OpenJDK runtime as a parent image
FROM amazoncorretto:17-alpine-jdk

# Set the working directory in the container
WORKDIR /stem

ARG JAR_FILE=build/libs/stem-0.0.1-SNAPSHOT.jar

# Copy the packaged JAR file into the container
COPY ${JAR_FILE} /stem/stem.jar

# Run the JAR file
ENTRYPOINT ["java", "-jar", "/stem/stem.jar"]
