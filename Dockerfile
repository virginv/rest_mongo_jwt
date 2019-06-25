FROM openjdk:8-jre-alpine
EXPOSE 8080
MAINTAINER virginv
ENV APP_FILE rest_mongo_jwt-0.0.1-SNAPSHOT.jar
ENV APP_HOME /usr/app
COPY target/$APP_FILE $APP_HOME/
WORKDIR $APP_HOME
ENTRYPOINT ["sh", "-c"]
CMD ["exec java -jar $APP_FILE"]
