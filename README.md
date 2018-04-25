# Watchitlater
### Setup environment variables
```javascript
$ export WATCHITLATER3_PORT=8090;
$ export WATCHITLATER3_DATASOURCE_URL=jdbc:mysql://localhost:3306/watchitlater3?serverTimezone=UTC
$ export WATCHITLATER3_DATASOURCE_USERNAME=root
$ export WATCHITLATER3_DATASOURCE_PASSWORD=password
$ export WATCHITLATER3_OMDBAPI_APIKEY=__use_ur_apikey_
$ export WATCHITLATER3_CONFIG_RUNUPDATEMOVIE=false
$ export WATCHITLATER3_BASEURL=....
```

### build (build fe to be resources then run be)
```javascript
$ mvn install -Plocal -DskipTests=true
or
$ mvn install -Pdev -DskipTests=true
then
$ java -jar backend/target/watchitlater-backend-1.0-SNAPSHOT.jar
open http://localhost:8090
```

### run backend
```javascript
firstly need to setup env like above
$ cd backend
$ mvn spring-boot:run
```

### run frontend
```javascript
$ cd frontend
$ ng serve --poll=1500;
open http://localhost:4200
login http://localhost:4200/login?email=alexander.petersen@example.com&password=password
```


### preparing sonarqube inside docker
```javascript
first time only, run it 
$ docker run -d --name sonarqube -p 9000:9000 -p 9092:9092 sonarqube
or start it
$ docker ps -a
$ docker start _sonarqube_container_id
```

### run all test class
```javascript
$ cd backend
$ mvn clean verify -s settings.xml sonar:sonar
open http://localhost:9000 (sonarqube)
```

### run singgle test class
```javascript
$ cd backend
$ mvn -Dtest=UpdateMovieScheduleTest#updateRatingMovie_noError test
```

### latest sonarqube screenshot
![alt text](https://raw.githubusercontent.com/tekdungtralala/watchitlater3/master/latest_sonar.png)