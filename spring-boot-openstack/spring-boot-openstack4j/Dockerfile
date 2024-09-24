FROM openjdk:8-jre-alpine
COPY target/*.jar /app.jar
EXPOSE 8080
ENTRYPOINT ["sh","-c","java -Xms128m -Xmx128m -Djava.security.egd=file:/dev/./urandom -jar /app.jar"]