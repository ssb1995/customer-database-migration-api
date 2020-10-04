FROM maven:3.5.2-jdk-8-alpine AS MAVEN_BUILD
MAINTAINER sathishbalajissb@gmail.com
COPY pom.xml /build/
COPY src /build/src/
WORKDIR /build/
RUN mvn package
FROM openjdk:8-jre-alpine
WORKDIR /app
COPY --from=MAVEN_BUILD /build/target/customerdbmigration-0.0.1-SNAPSHOT.jar /app/
ENTRYPOINT ["java", "-jar", "customerdbmigration-0.0.1-SNAPSHOT.jar"]

#docker image build -t customerdbmigration .
#docker container run -p 8080:8080 customerdbmigration
#docker tag customerdbmigration:latest sathishssb/customerdbmigration:V0.0.1-SNAPSHOT
#docker push sathishssb/customerdbmigration:V0.0.1-SNAPSHOT