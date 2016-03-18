
FROM tomcat:8.0.20-jre8

MAINTAINER arturdaveyan@gmail.com

RUN apt-get update
RUN apt-get install -y maven
RUN apt-get install -y default-jdk

WORKDIR /code

ADD pom.xml /code/pom.xml
RUN ["mvn", "dependency:resolve"]
RUN ["mvn", "verify"]

ADD src /code/src
RUN ["mvn", "package"]

RUN cp /code/target/testTask.war /usr/local/tomcat/webapps/testTask.war

EXPOSE 8080
