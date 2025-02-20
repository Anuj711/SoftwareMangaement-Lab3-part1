FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
COPY target/*.war /binarycalculator.war
ENTRYPOINT ["java","-jar","/binarycalculator.war"]
