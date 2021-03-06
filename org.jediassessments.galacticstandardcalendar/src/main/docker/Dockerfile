# Stage 1 : build with maven image
FROM maven:3.6.3-openjdk-15 AS build
ARG REPOCACHEVERSION=0
LABEL stage=builder
COPY pom.xml /usr/bootstrap/
COPY src/main/docker/BootstrapTest.java /usr/bootstrap/src/test/java/
RUN mvn -f /usr/bootstrap/pom.xml clean package && rm -r /usr/bootstrap/target
ARG BUILDCACHEVERSION=0
ARG SKIPTESTS=false
LABEL version=${BUILDCACHEVERSION}
COPY pom.xml /usr/src/app/
COPY src /usr/src/app/src
RUN mvn -f /usr/src/app/pom.xml clean test -Dmaven.test.skip=${SKIPTESTS}
RUN MAVEN_OPTS="--enable-preview" mvn -f /usr/src/app/pom.xml package -Dmaven.test.skip=true

# Stage 2 : create the docker final image
FROM adoptopenjdk/openjdk15:jre-15.0.2_7-alpine 
ARG BUILDCACHEVERSION=0
LABEL stage=runner
LABEL version=${BUILDCACHEVERSION}
COPY --from=build /usr/src/app/target/quarkus-app /opt/application/quarkus-app

# set up permissions for user `1001`
RUN chmod 775 /opt/application/quarkus-app \
  && chown -R 1001 /opt/application/quarkus-app \
  && chmod -R "g+rwX" /opt/application/quarkus-app \
  && chown -R 1001:root /opt/application/quarkus-app

EXPOSE 8080
USER 1001

ENTRYPOINT ["java", "--enable-preview", "-jar", "/opt/application/quarkus-app/quarkus-run.jar"]
