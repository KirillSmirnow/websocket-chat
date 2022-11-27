FROM openjdk:17
WORKDIR /opt
COPY target/*.jar .
ENTRYPOINT exec java -Xms128M -Xmx512M -Dspring.profiles.active=$PROFILE -jar *.jar
