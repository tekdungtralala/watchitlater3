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


### backend test - preparing sonarqube inside docker
```javascript
first time only, run it 
$ docker run -d --name sonarqube -p 9000:9000 -p 9092:9092 sonarqube
or start it
$ docker ps -a
$ docker start _sonarqube_container_id
```

### backend test - run all test class
```javascript
$ cd backend
$ mvn clean verify -s settings.xml sonar:sonar
open http://localhost:9000 (sonarqube)
```

### backend test - run singgle test class
```javascript
$ cd backend
$ mvn -Dtest=UpdateMovieScheduleTest#updateRatingMovie_noError test
```

### backend test - latest sonarqube screenshot
![alt text](https://raw.githubusercontent.com/tekdungtralala/watchitlater3/master/latest_sonar.png)


### frontend test - version check
```javascript
node v10.0.0
java 1.8.0_162
angular 4.2.4
protractor 5.3.1
webdriver-manager 12.0.6
chromedriver 2.38
selenium standalone 3.11.0
Browser: Chrome 66.0.3359.139
Operating System : Mac OS 10.13.4
```

### frontend test - update & run webdriver
```javascript
$ cd frontend-integration-test
$ ./node_modules/protractor/bin/webdriver-manager update
$ ./node_modules/protractor/bin/webdriver-manager start
```

### frontend test - run test
```javascript
# dont forget to run springboot app first before run e2e test
$ ./node_modules/protractor/bin/protractor src/conf.js
```

### frontend test - latest protractor screenshot
![alt text](https://raw.githubusercontent.com/tekdungtralala/watchitlater3/master/latest_protractor.png)