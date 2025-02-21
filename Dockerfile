# Use a Java runtime (Eclipse Temurin 17 JDK) as the base image
FROM eclipse-temurin:17-jdk-alpine

# Set up the volume for temporary files (optional for Java apps, often used with Tomcat)
VOLUME /tmp

# Copy the WAR file into the container
COPY target/binarycalculator.war /binarycalculator.war

# Run the WAR file using the Java command
ENTRYPOINT ["java", "-jar", "/binarycalculator.war"]
