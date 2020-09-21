FROM openjdk:11.0.8
WORKDIR main
ADD /src/webserver/main /main
ADD /src/pages /pages
ENTRYPOINT ["java", "-jar", "WebServer.jar", "../pages", "8080"]
