# set environment properties for springboot configuration
ENV JAVA_OPTS "-Xmx1024m -Xms512m"
ENV SERVER_PORT 8080


ENV JAVA_HOME="/usr/lib/jvm/default-jvm/"
RUN apk add openjdk11

ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar

EXPOSE ${SERVER_PORT}
ENTRYPOINT ["java","-jar","/app.jar"]