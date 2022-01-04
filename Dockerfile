FROM openjdk:15-alpine
MAINTAINER erik_10_1997@hotmail.com
COPY "target/prueba-tecnica-*.jar" "app.jar"
EXPOSE 8090
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","app.jar"]