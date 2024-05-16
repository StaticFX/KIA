FROM gradle:8.3.0 as build

WORKDIR /app

COPY . .

RUN --mount=type=cache,target=/root/.gradle gradle build --parallel --no-daemon

FROM openjdk:20-slim as serverBuilder

WORKDIR /app

ADD https://api.papermc.io/v2/projects/paper/versions/1.20.4/builds/496/downloads/paper-1.20.4-496.jar server/paper.jar
RUN echo 'eula=true' > server/eula.txt

COPY --from=build /app/build/libs/KIA-*-all.jar server/plugins/app.jar

EXPOSE 22565
VOLUME /server


WORKDIR /app/server
CMD ["java", "-jar", "paper.jar"]
