# testTask_refactored
test task for Akvelon java junior developer
REST API :  https://docs.google.com/spreadsheets/d/1yoaKXn4a7gsRR6OUdP91T9mS4IQ-IRfeyyzByf66VzA/edit?usp=sharing


 first add ".jar" to sqljdbc4 file's name in project's root folder
To build and deploy perform commands in source folder:

    mvn install:install-file -Dfile=sqljdbc4.jar -DgroupId=com.microsoft.sqlserve4 -DartifactId=sqljdbc4 -Dversion=4.0 -Dpackaging=jar
    mvn package

   // then in docker:

   docker build .

   docker run -it -p 8087:8080 ... bin\bash

   // where ... is built image hash shown after build success
