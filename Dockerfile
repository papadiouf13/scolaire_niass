FROM openjdk:17
WORKDIR /app
COPY target/scolaire_niass.war /app/scolarite.war 
CMD [ "java", "-jar", "servlet.war" ]