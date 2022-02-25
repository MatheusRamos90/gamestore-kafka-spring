FROM openjdk:11
MAINTAINER matheushramos
COPY build/libs/*.jar app.jar
ENTRYPOINT ["java", "-Dspring.data.mongodb.uri=mongodb://root:root@mongodb:27017/gamestorekafka?authSource=admin", "-jar", "app.jar"]