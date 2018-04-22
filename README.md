$ mvn clean verify -s settings.xml sonar:sonar
$ mvn clean pacakge -DskipTests=true
$ mvn install -DskipTests=true
$ mvn install -Plocal -DskipTests=true
$ mvn install -Pdev -DskipTests=true
$ java -jar backend/target/watchitlater-backend-1.0-SNAPSHOT.jar