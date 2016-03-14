# testTask_refactored
### test task for Akvelon java junior developer

```
REST API :  https://docs.google.com/spreadsheets/d/1yoaKXn4a7gsRR6OUdP91T9mS4IQ-IRfeyyzByf66VzA/edit?usp=sharing
```

### notes:
```
you can skip configuring your own database if you want. By keeping db.properties as they are, app will be connected over external ip to the database running on my pc
(it's online almost 24/7 and contains no critical data so i don't care about security sharing even the admin credentials =) ).
Otherwise use SQL script and mdf file provided by link below:
```
https://drive.google.com/folderview?id=0Bye2GvHai26DaEk1Tks5UlBJM0U&usp=sharing
```
```

  ```
  first add ".jar" to sqljdbc4 file's name in project's root folder
  ```
# Deployment:
### To build and deploy perform commands in source folder:

    mvn install:install-file -Dfile=sqljdbc4.jar -DgroupId=com.microsoft.sqlserve4 -DartifactId=sqljdbc4 -Dversion=4.0 -Dpackaging=jar
    mvn package


### then run next docker commands:
   docker build -t testtask .
   docker run -it -p 8087:8080 testtask bin\bash

