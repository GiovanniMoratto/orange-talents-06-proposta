FROM openjdk:11
MAINTAINER Giovanni Moratto
WORKDIR proposta
EXPOSE 8082
COPY target/*.jar proposta.jar
CMD ["java", "-jar", "proposta.jar"]