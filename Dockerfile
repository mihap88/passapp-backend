FROM maven:latest as MAVEN
RUN mkdir /usr/src/app-backend
WORKDIR /usr/src/app-backend
COPY pom.xml /usr/src/app-backend/pom.xml
COPY src/ /usr/src/app-backend/src/

RUN mvn package

FROM openjdk:latest
RUN mkdir /usr/src/app-backend
WORKDIR /usr/src/app-backend
COPY --from=MAVEN /usr/src/app-backend/target/backend-0.0.1-SNAPSHOT.jar /usr/src/app-backend/target.jar

ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "./target.jar"]