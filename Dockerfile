FROM openjdk:11-jre
WORKDIR /
COPY target/MovieRecSys-1.0-SNAPSHOT-jar-with-dependencies.jar /MovieRecSys-1.0-SNAPSHOT-jar-with-dependencies.jar
EXPOSE 6010
CMD java -jar MovieRecSys-1.0-SNAPSHOT-jar-with-dependencies.jar
