# Let's combine maven jar building and image creation for simplifying running the app
FROM maven AS MAVEN_BUILD
COPY src /home/note-service/src
COPY pom.xml /home/note-service
RUN mvn -f /home/note-service/pom.xml clean package

FROM openjdk
COPY --from=MAVEN_BUILD /home/note-service/target/note-service-0.0.1.jar /usr/local/lib/note-service.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/usr/local/lib/note-service.jar"]
