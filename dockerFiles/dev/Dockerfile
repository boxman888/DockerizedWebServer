FROM openjdk:11.0.8
ADD src/webserver /webserver
ADD src/pages /pages
WORKDIR webserver
ENTRYPOINT ["java",  "WebServer", "../pages"]
