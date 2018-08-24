# Dashboard

## About Dashboard

This application consists of three widgets and displays the following information:
1. Current weather and forecast for tomorrow with a choice of the city (source: Yandex.Weather).
2. Currency exchange rates of the Euro and the Dollar in relation to the Ruble (source: Central Bank of the Russian Federation).
3. The number of visits to the application, while retaining the value in the MongoDB.

Also, the application displays the user's IP address

## Installing

After cloning the repository, go to the directory with Dashboard and type in the terminal
```
mvn install
```
After this command, a target directory will appear. In this directory you can find dashboard-0.0.1-SNAPSHOT.war file. 
Now you can move this file to tomcat/webapps directory and run in your browser:
```
http://localhost:8080/dashboard-0.0.1-SNAPSHOT/
```

## Built With

 1. Spring Boot
 2. Vaadin
 3. Apache Tomcat
 4. MongoDB
 5. Maven
 6. Log4j2
 
 ## Logs
 
 Application logs are stored in project's directory **/logs**:
 1. **root.log** - main application log 
 2. **service.log** - event handling of the service layer
 
## Author

Dmitry Syusin, sitric.grp@gmail.com