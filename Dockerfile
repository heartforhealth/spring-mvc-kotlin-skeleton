# gcloud builds submit --tag eu.gcr.io/$(gcloud config get-value project)/<container-name>:test .



# =====================================================
# builder stage
# =====================================================
FROM gradle:7.3.3-jdk17-alpine as builder
WORKDIR /usr/src/app/
COPY . .
RUN gradle clean build --no-daemon


# =====================================================
# production stage, slimmer container
# BTW, alpine container will crash
# =====================================================
FROM eclipse-temurin:17-jre

# Create a group and user
RUN addgroup --system --gid 1001 appuser
RUN adduser --system --uid 1001 --group appuser
# All future commands should run as the appuser user
USER appuser

# Setup working directory
WORKDIR /home/appuser

COPY --from=builder /usr/src/app/build/libs/*-SNAPSHOT.jar /home/appuser/app/app.jar
COPY --from=builder /usr/src/app/elastic /home/appuser/app/elastic

EXPOSE 8080

ENTRYPOINT ["java", \
    "-javaagent:/home/appuser/app/elastic/elastic-apm-agent-1.28.4.jar", \
    "-jar", "/home/appuser/app/app.jar", \
    "--spring.config.additional-location=optional:file:/configuration/"]
