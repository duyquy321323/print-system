FROM jelastic/maven:3.9.5-openjdk-21 AS build
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:21-jdk-slim
COPY --from=build /target/printer_system-0.0.1-SNAPSHOT.jar printer_system-0.0.1-SNAPSHOT.jar
EXPOSE 8080
CMD [ "java","-jar","printer_system-0.0.1-SNAPSHOT.jar" ]