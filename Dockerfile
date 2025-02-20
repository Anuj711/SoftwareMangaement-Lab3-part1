FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
COPY target/*.war /binarycalculator.jar
ENTRYPOINT ["java","-jar","/binarycalculator.jar"]
