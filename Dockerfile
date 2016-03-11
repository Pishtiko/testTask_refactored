
FROM tomcat:8.0.20-jre8


COPY /target/secure-exam.war /usr/local/tomcat/webapps/secure-exam.war

EXPOSE 8080
