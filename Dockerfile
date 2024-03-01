FROM gradle:8.3.0 as build

WORKDIR /app

COPY . .
RUN shadowJar

FROM openjdk:20-slim as serverBuilder

WORKDIR /app

ADD https://api.papermc.io/v2/projects/paper/versions/1.20.1/builds/196/downloads/paper-1.20.1-196.jar paper.jar
RUN echo 'eula=true' > eula.txt

RUN java -jar paper.jar
COPY --from=build /build