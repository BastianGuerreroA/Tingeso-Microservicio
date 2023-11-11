FROM openjdk:17
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} Eureka.jar
ENTRYPOINT ["java","-jar","/Eureka.jar"]