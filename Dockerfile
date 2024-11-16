FROM openjdk:17
WORKDIR /app
COPY target/scolaire-0.0.1-SNAPSHOT.jar /app/scolarite.jar
RUN ls -la /app
CMD [ "java", "-jar", "scolarite.jar" ]
