FROM gradle:8.7.0 as build

WORKDIR /app

COPY . .

RUN --mount=type=cache,target=/root/.gradle gradle build --parallel --no-daemon

FROM openjdk:21-slim as serverBuilder

WORKDIR /app

COPY entrypoint.sh /app/entrypoint.sh

ADD https://api.papermc.io/v2/projects/paper/versions/1.20.4/builds/496/downloads/paper-1.20.4-496.jar server/paper.jar
RUN echo 'eula=true' > server/eula.txt

COPY --from=build /app/build/libs/KIA-*-all.jar /app/tmp/app.jar

ENTRYPOINT ["/app/entrypoint.sh"]

EXPOSE 22565
EXPOSE 5005