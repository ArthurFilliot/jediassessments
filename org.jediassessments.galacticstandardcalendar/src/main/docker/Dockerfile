# Stage 1 : build with maven image
FROM maven:3.6.3-openjdk-15 AS build
COPY pom.xml /usr/src/app/
COPY pom.xml /usr/bootstrap/
COPY src/main/docker/BootstrapTest.java /usr/bootstrap/src/test/java/
COPY src /usr/src/app/src
RUN mvn -f /usr/bootstrap/pom.xml clean package && rm -r /usr/bootstrap/target
ARG CHECKPOINT=reset
RUN MAVEN_OPTS="--enable-preview" mvn -f /usr/src/app/pom.xml clean package

# Stage 2 : create the docker final image
FROM adoptopenjdk/openjdk15:jre-15.0.2_7-alpine 
COPY --from=build /usr/src/app/target/quarkus-app /opt/application/quarkus-app

# set up permissions for user `1001`
RUN chmod 775 /opt/application/quarkus-app \
  && chown -R 1001 /opt/application/quarkus-app \
  && chmod -R "g+rwX" /opt/application/quarkus-app \
  && chown -R 1001:root /opt/application/quarkus-app

EXPOSE 8080
USER 1001

ENTRYPOINT ["java", "--enable-preview", "-jar", "/opt/application/quarkus-app/quarkus-run.jar"]
