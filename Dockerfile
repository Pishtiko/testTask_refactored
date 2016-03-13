
FROM tomcat:8.0.20-jre8

MAINTAINER: Artur-Daveyan

COPY /target/secure-exam.war /usr/local/tomcat/webapps/secure-exam.war

EXPOSE 8080
