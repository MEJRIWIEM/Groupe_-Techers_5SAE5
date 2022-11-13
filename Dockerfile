FROM maven as build
WORKDIR /app
COPY . .
RUN mvn install

FROM openjdk:8-jdk-alpine
EXPOSE 8089
RUN ls
ADD target/achat-1.0-SNAPSHOT.jar achat-1.0-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/achat-1.0-SNAPSHOT.jar"]