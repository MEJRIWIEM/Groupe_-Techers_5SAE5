FROM openjdk:8-jre-alpine
ADD target/achat-1.0-SNAPSHOT.jar achat-1.0-SNAPSHOT.jar
EXPOSE 8080
CMD ["java", "-jar", "/achat-1.0-SNAPSHOT.jar"]